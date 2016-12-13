package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.*;

import java.util.*;

/**
 * Created by devinmorgan on 11/20/16.
 */
public class CFG {

    private final LlBuilder builder;
    private final ArrayList<BasicBlock> basicBlocks;
    private final ArrayList<String> orderedLeadersList;
    private final LinkedHashMap<String, BasicBlock> leadersToBBMap;
    private final LinkedHashMap<BasicBlock, String> blockLabels;

    public ArrayList<LlLocationVar> getParamsList() {
        return paramsList;
    }

    private final ArrayList<LlLocationVar> paramsList;

    public CFG(LlBuilder builder) {
        this.builder = builder;

        this.paramsList = builder.params;
        // cache the Labels => Stmts map and extract the labels list
        LinkedHashMap<String, LlStatement> labelStmtsMap = new LinkedHashMap<>(builder.getStatementTable());
        ArrayList<String> labelsList = new ArrayList<>(labelStmtsMap.keySet());

        if (labelsList.size() == 0) {
            this.basicBlocks = new ArrayList<BasicBlock>();
            this.orderedLeadersList = new ArrayList<>();
            this.leadersToBBMap = new LinkedHashMap<>();
            this.blockLabels = new LinkedHashMap<>();
        }
        else {
            // 1) determine the leaders in the LLIR
            HashSet<String> leadersSet = new HashSet<>();

            // the first instruction in the LLIR is a leader
            leadersSet.add(labelsList.get(0));

            for (int i = 1; i < labelsList.size(); i++) {
                String label = labelsList.get(i);
                LlStatement stmt = labelStmtsMap.get(label);

                if (stmt instanceof LlJump) {

                    // the TARGET of the jumpStmt is a leader
                    String jmpTolabel = ((LlJump) stmt).getJumpToLabel();
                    leadersSet.add(jmpTolabel);

                    // the stmt FOLLOWING the jumpStmt is a leader
                    String nextStmtLabel = labelsList.get(i + 1);
                    leadersSet.add(nextStmtLabel);
                }
            }

            // 2) create basic blocks from LlStatements
            this.leadersToBBMap = new LinkedHashMap<>();
            HashSet<String> tempLeadersSet = new HashSet<>(leadersSet);
            LinkedList<String> labelsQueue = new LinkedList<>(labelsList);
            this.orderedLeadersList = new ArrayList<>();
            do {
                LinkedHashMap<String, LlStatement> bbLabelsToStmtsMap = new LinkedHashMap<>();

                // basic blocks start with the leader
                String leaderLabel = labelsQueue.pop();
                LlStatement leaderStmt = labelStmtsMap.get(leaderLabel);
                bbLabelsToStmtsMap.put(leaderLabel, leaderStmt);

                // remove this leader from the leadersSet and add it
                // to the leadersList
                tempLeadersSet.remove(leaderLabel);
                this.orderedLeadersList.add(leaderLabel);

                // keep adding LlStatments until you get to the next leader
                while (labelsQueue.size() > 0 && !tempLeadersSet.contains(labelsQueue.peek())) {

                    // keep adding stmts to the currentBBStmtList
                    String label = labelsQueue.pop();
                    LlStatement stmt = labelStmtsMap.get(label);
                    bbLabelsToStmtsMap.put(label, stmt);
                }

                // create the actual BasicBlock and it to the LinkedHashMap
                BasicBlock bb = new BasicBlock(bbLabelsToStmtsMap, builder);
                this.leadersToBBMap.put(leaderLabel, bb);

            } while (labelsQueue.size() > 0);

            // 3) appropriately connect the basic blocks
            for (int i = 0; i < this.orderedLeadersList.size(); i++) { // loop through the leaders in the order of the linear order of the basic blocks
                String leaderLabel = this.orderedLeadersList.get(i);
                BasicBlock bb = this.leadersToBBMap.get(leaderLabel);

                List<LlStatement> bbStmtsList = bb.getStmtsList();
                LlStatement lastStmtOfCurrentBB = bbStmtsList.get(bbStmtsList.size() - 1);

                // connect if there is a jump from the end of B to the beginning of C
                if (lastStmtOfCurrentBB instanceof LlJump) {

                    // set forward edge B --> C
                    String targetLabel = ((LlJump) lastStmtOfCurrentBB).getJumpToLabel();
                    BasicBlock targetBB = this.leadersToBBMap.get(targetLabel);
                    bb.setAlternativeBranch(targetBB);

                    // set reverse edge B <-- C
                    targetBB.addPredecessorNode(bb);
                }

                // C immediately follows B and B does not end in an unconditional jump
                // (this only holds if B is not the last block))
                if (!(lastStmtOfCurrentBB instanceof LlJumpUnconditional) && (i < this.orderedLeadersList.size() - 1)) {

                    // set forward edge B --> C
                    String nextBBLeaderLabel = this.orderedLeadersList.get(i + 1);
                    BasicBlock nextBB = this.leadersToBBMap.get(nextBBLeaderLabel);
                    bb.setDefaultBranch(nextBB);

                    // set reverse edge B --> C
                    nextBB.addPredecessorNode(bb);
                }
            }

            // add an empty BasicBlock as the entry node and
            // connect it and the orignal first BB to each other
            String trueFirstBBLabel = this.orderedLeadersList.get(0);
            BasicBlock trueFirstBB = this.leadersToBBMap.get(trueFirstBBLabel);

            String entryBBLabel = "entry";
            this.orderedLeadersList.add(0, entryBBLabel);
            LinkedHashMap<String, LlStatement> entryBBStmtsList = new LinkedHashMap<>();
            entryBBStmtsList.put(entryBBLabel, new LlEmptyStmt());
            BasicBlock entryBB = new BasicBlock(entryBBStmtsList, builder);
            this.leadersToBBMap.put(entryBBLabel, entryBB);

            trueFirstBB.addPredecessorNode(entryBB);
            entryBB.setDefaultBranch(trueFirstBB);

            // add an empty BasicBlock as the exit node and
            // connect it and the orignal last BB to each other
            String trueLastBBLabel = this.orderedLeadersList.get(this.orderedLeadersList.size() - 1);
            BasicBlock trueLastBB = this.leadersToBBMap.get(trueLastBBLabel);

            String exitBBLabel = "eixt";
            this.orderedLeadersList.add(exitBBLabel);
            LinkedHashMap<String, LlStatement> exitBBStmtsList = new LinkedHashMap<>();
            exitBBStmtsList.put(exitBBLabel, new LlEmptyStmt());
            BasicBlock exitBB = new BasicBlock(exitBBStmtsList, builder);
            this.leadersToBBMap.put(exitBBLabel, exitBB);

            trueLastBB.addPredecessorNode(exitBB);
            exitBB.setDefaultBranch(trueLastBB);

            // 5) assign the list of basic blocks as a field of THIS object
            ArrayList<BasicBlock> basicBlocks = new ArrayList<>();
            for (String leaderLabel : this.orderedLeadersList) {
                basicBlocks.add(this.leadersToBBMap.get(leaderLabel));
            }
            this.blockLabels = new LinkedHashMap<>(reverse(leadersToBBMap));
            this.basicBlocks = basicBlocks;
        }
    }

    public LlBuilder getBuilder() {
        return this.builder;
    }

    public BasicBlock getRootBasicBlock() {
        return this.basicBlocks.get(0);
    }

    public ArrayList<BasicBlock> getBasicBlocks() {
        return this.basicBlocks;
    }

    @Override
    public String toString() {
        String str = "CFG:\n";
        for (BasicBlock bb : this.basicBlocks) {
            str += bb.toString() + "\n";
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    // ================= Tuple =================

    private final Tuple noDefTuple = new Tuple("NO_DEF_1010", "NO_DEF_1010");

    public Tuple getNoDefTuple() {
        return this.noDefTuple;
    }
    public class Tuple {
        public String blockName;

        public String label;

        public Tuple(String x, String y) {
            this.blockName = x;
            this.label = y;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof Tuple) && (((Tuple) o).blockName.equals(this.blockName)) &&
                    (((Tuple) o).label.equals(this.label));
        }
        @Override
        public int hashCode() {
            return this.blockName.hashCode() + this.label.hashCode();
        }

        @Override
        public String toString() {
            return "(" + this.blockName + ", " + this.label + ")";
        }

    }

    // ================= Symbol Def =================

    public class SymbolDef {
        public LlLocation symbol;

        public Tuple useDef;

        public SymbolDef(LlLocation symbol, Tuple useDef) {
            this.useDef = useDef;
            this.symbol = symbol;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof SymbolDef) && (((SymbolDef) o).symbol.equals(this.symbol)) &&
                    (((SymbolDef) o).useDef.equals(this.useDef));
        }
        @Override
        public int hashCode() {
            return this.symbol.hashCode() + this.useDef.hashCode();
        }

        @Override
        public String toString() {
            return this.symbol.toString() + " : " + this.useDef.toString();
        }

    }

    // ================= USE-DEF Chain =================
    private HashMap<SymbolDef, ArrayList<Tuple>> defUseChain = new HashMap<>();
    private HashMap<SymbolDef, ArrayList<Tuple>> useDefChain = new HashMap<>();
    private HashSet<Edge> isVisited = new HashSet<>();

    //Mark use of arg at currentUseDefLocation in defUseChain using recentDef
    private void addUseArg(HashMap<LlLocation, Tuple> recentDef, LlComponent arg, Tuple currentUseDefLocation) {
        if (arg instanceof LlLocation) {

            //Default value (0) being used in arg
            if (!recentDef.containsKey(arg)) {
                recentDef.put((LlLocation) arg, this.noDefTuple);
                this.defUseChain.put(new SymbolDef((LlLocation) arg, this.noDefTuple), new ArrayList<>());
            }

            //Add use to ArrayList of uses corresponding to recent def of LlLocation arg
            Tuple latestDef = recentDef.get(arg);
            ArrayList<Tuple> useList = this.defUseChain.get(new SymbolDef((LlLocation) arg, latestDef));
            useList.add(currentUseDefLocation);
        }

        if (arg instanceof LlLocationArray) {
            LlLocationVar indexArg = ((LlLocationArray) arg).getElementIndex();
            this.addUseArg(recentDef, indexArg, currentUseDefLocation);
        }
    }

    //Recursively (DFS) build defUseChains
    private void buildDefUseRecursive(BasicBlock head, HashMap<LlLocation, Tuple> recentDef) {
        //Add def-use chains of basic block head
        if(head == null){
            return ;
        }
        for (Map.Entry<String, LlStatement> statementRow : head.getLabelsToStmtsMap().entrySet()) {
            String label = statementRow.getKey();
            LlStatement statement = statementRow.getValue();

            //Tuple corresponding to location (blockName, label) of current statement
            //All uses and defs in statement happen at this location
            Tuple currentUseDefLocation = new Tuple(blockLabels.get(head), label);

            //Method call statements
            if (statement instanceof LlMethodCallStmt) {
                //Mark useDef for returnLocation
                if (((LlMethodCallStmt) statement).getReturnLocation() != null) {
                    LlLocation returnLocation = ((LlMethodCallStmt) statement).getReturnLocation();
                    SymbolDef currentSymbolDef = new SymbolDef(returnLocation, currentUseDefLocation);

                    recentDef.put(returnLocation, currentUseDefLocation);
                    this.defUseChain.put(currentSymbolDef, new ArrayList<>());

                    //Get index from array def call
                    if (returnLocation instanceof LlLocationArray) {
                        LlLocationVar indexArg = ((LlLocationArray) returnLocation).getElementIndex();
                        this.addUseArg(recentDef, indexArg, currentUseDefLocation);
                    }
                }

                //Mark use for argsList values
                for (LlComponent arg : ((LlMethodCallStmt) statement).getArgsList()) {
                    this.addUseArg(recentDef, arg, currentUseDefLocation);
                }
            }

            //Conditional jump statements
            else if (statement instanceof LlJumpConditional) {
                LlComponent arg = ((LlJumpConditional) statement).getCondition();

                //Mark use of arg location
                this.addUseArg(recentDef, arg, currentUseDefLocation);
            }

            //Return statements
            else if (statement instanceof LlReturn) {
                //Mark variable use for return statement
                LlComponent arg = ((LlReturn) statement).getReturnValue();
                this.addUseArg(recentDef, arg, currentUseDefLocation);
            }

            //Assign statements and sub-class statements
            else if (statement instanceof LlAssignStmt) {

                //Mark useDef for storeLocation
                LlLocation returnLocation = ((LlAssignStmt) statement).getStoreLocation();
                SymbolDef currentSymbolDef = new SymbolDef(returnLocation, currentUseDefLocation);

                recentDef.put(returnLocation, currentUseDefLocation);
                this.defUseChain.put(currentSymbolDef, new ArrayList<>());

                //Get index from array def call
                if (returnLocation instanceof LlLocationArray) {
                    LlLocationVar indexArg = ((LlLocationArray) returnLocation).getElementIndex();
                    this.addUseArg(recentDef, indexArg, currentUseDefLocation);
                }

                if (statement instanceof LlAssignStmtRegular) {
                    //Mark use of arg location
                    LlComponent arg = ((LlAssignStmtRegular) statement).getOperand();
                    this.addUseArg(recentDef, arg, currentUseDefLocation);
                }

                else if (statement instanceof LlAssignStmtUnaryOp) {
                    //Mark use of arg location
                    LlComponent arg = ((LlAssignStmtUnaryOp) statement).getOperand();
                    this.addUseArg(recentDef, arg, currentUseDefLocation);
                }

                else if (statement instanceof LlAssignStmtBinaryOp) {
                    //Mark use of leftArg and rightArg location
                    LlComponent leftArg = ((LlAssignStmtBinaryOp) statement).getLeftOperand();
                    LlComponent rightArg = ((LlAssignStmtBinaryOp) statement).getRightOperand();
                    this.addUseArg(recentDef, leftArg, currentUseDefLocation);
                    this.addUseArg(recentDef, rightArg, currentUseDefLocation);
                }
            }
        }

        //Visit default and alternate branches to continue depth first search
        Edge left = head.getLeft();
        Edge right = head.getRight();

        if (left != null && !isVisited.contains(left)) {
            isVisited.add(head.getLeft());
            buildDefUseRecursive(head.getDefaultBranch(), new HashMap<>(recentDef));
        }

        if (right != null && !isVisited.contains(right)) {
            isVisited.add(head.getRight());
            buildDefUseRecursive(head.getAlternativeBranch(), new HashMap<>(recentDef));
        }
    }

    //Build def-use chains for each symbol from updated/changed LlBuilder
    public HashMap<SymbolDef, ArrayList<Tuple>> buildDefUseChains() {
        this.defUseChain = new HashMap<>();
        BasicBlock head = basicBlocks.get(0);
        HashMap<LlLocation, Tuple> recentDef = new HashMap<>();
        buildDefUseRecursive(head, recentDef);

        //All uses and defs in statement happen at this location
        Tuple firstUseDefLocation = new Tuple(blockLabels.get(head), "L0");

//        for (LlLocationVar paramArg : this.paramsList) {
//            SymbolDef currentSymbolDef = new SymbolDef(paramArg, firstUseDefLocation);
//            recentDef.put(paramArg, firstUseDefLocation);
//            this.defUseChain.put(currentSymbolDef, new ArrayList<>());
//        }

//        //Print statements for useDefChains
        for (Map.Entry<SymbolDef, ArrayList<Tuple>> chain : this.defUseChain.entrySet()) {
            System.out.println(chain.getKey().toString() + " -> " + chain.getValue().toString());
        }
        return this.defUseChain;
    }

    //Build use-def chains for each symbol from updated/changed LlBuilder
    public HashMap<SymbolDef, ArrayList<Tuple>> buildUseDefChains() {
        this.buildDefUseChains();
        this.useDefChain = new HashMap<>();
        for (Map.Entry<SymbolDef, ArrayList<Tuple>> duChain : this.defUseChain.entrySet()) {
            ArrayList<Tuple> useList = duChain.getValue();
            LlLocation symbol = duChain.getKey().symbol;
            Tuple defLocation = duChain.getKey().useDef;

            //Add reaching defs for each corresponding use to useDefChain
            for (Tuple useLocation : useList) {
                SymbolDef currentUseSymbol = new SymbolDef(symbol, useLocation);
                if (!this.useDefChain.containsKey(currentUseSymbol)) {
                    ArrayList<Tuple> defList = new ArrayList<>();
                    defList.add(defLocation);
                    this.useDefChain.put(currentUseSymbol, defList);
                }
                else {
                    this.useDefChain.get(currentUseSymbol).add(defLocation);
                }
            }
        }
        return this.useDefChain;
    }

    public ArrayList<String> getOrderedLeadersList() {
        return new ArrayList<>(orderedLeadersList);
    }

    public LinkedHashMap<String, BasicBlock> getLeadersToBBMap() {
        return new LinkedHashMap<>(leadersToBBMap);
    }

    private static HashMap<BasicBlock, String> reverse(Map<String, BasicBlock> map) {
        HashMap<BasicBlock, String> rev = new HashMap<BasicBlock, String>();
        for(Map.Entry<String, BasicBlock> entry : map.entrySet())
            rev.put(entry.getValue(), entry.getKey());
        return rev;
    }

}
