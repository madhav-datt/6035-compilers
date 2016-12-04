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
    private final LinkedHashMap<BasicBlock, String> blockLabels;

    public Tuple getNoDefTuple() {
        return this.noDefTuple;
    }

    private final Tuple noDefTuple = new Tuple("NO_DEF", "NO_DEF");

    public LlBuilder getBuilder() {
        return this.builder;
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
    }

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
    }

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
    }

    //Recursively (DFS) build defUseChains
    private void buildDefUseRecursive(BasicBlock head, HashMap<LlLocation, Tuple> recentDef) {
        //Add def-use chains of basic block head
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

                if (statement instanceof LlAssignStmtRegular) {
                    //Mark use of arg location
                    LlComponent arg = ((LlAssignStmtRegular) statement).getArg();
                    this.addUseArg(recentDef, arg, currentUseDefLocation);
                }

                else if (statement instanceof LlAssignStmtUnaryOp) {
                    //Mark use of arg location
                    LlComponent arg = ((LlAssignStmtUnaryOp) statement).getArg();
                    this.addUseArg(recentDef, arg, currentUseDefLocation);
                }

                else if (statement instanceof LlAssignStmtBinaryOp) {
                    //Mark use of leftArg and rightArg location
                    LlComponent leftArg = ((LlAssignStmtBinaryOp) statement).getLeftArg();
                    LlComponent rightArg = ((LlAssignStmtBinaryOp) statement).getRightArg();
                    this.addUseArg(recentDef, leftArg, currentUseDefLocation);
                    this.addUseArg(recentDef, rightArg, currentUseDefLocation);
                }
            }
        }

        //Visit default and alternate branches to continue depth first search
        Edge left = head.getLeft();
        Edge right = head.getRight();

        if (!isVisited.contains(left)) {
            isVisited.add(head.getLeft());
            buildDefUseRecursive(head.getDefaultBranch(), new HashMap<>(recentDef));
        }

        if (!isVisited.contains(right)) {
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

    public CFG(LlBuilder builder) {
        this.builder = builder;

        // cache the Labels => Stmts map and extract the labels list
        LinkedHashMap<String, LlStatement> labelStmtsMap = new LinkedHashMap<>(builder.getStatementTable());
        ArrayList<String> labelsList = new ArrayList<>(labelStmtsMap.keySet());

        if (labelsList.size() == 0) {
            this.basicBlocks = new ArrayList<BasicBlock>();
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
            LinkedHashMap<String, BasicBlock> leadersToBBMap = new LinkedHashMap<>();
            HashSet<String> tempLeadersSet = new HashSet<>(leadersSet);
            LinkedList<String> labelsQueue = new LinkedList<>(labelsList);
            ArrayList<String> orderedLeadersList = new ArrayList<>();
            do {
                LinkedHashMap<String, LlStatement> bbLabelsToStmtsMap = new LinkedHashMap<>();

                // basic blocks start with the leader
                String leaderLabel = labelsQueue.pop();
                LlStatement leaderStmt = labelStmtsMap.get(leaderLabel);
                bbLabelsToStmtsMap.put(leaderLabel, leaderStmt);

                // remove this leader from the leadersSet and add it
                // to the leadersList
                tempLeadersSet.remove(leaderLabel);
                orderedLeadersList.add(leaderLabel);

                // keep adding LlStatments until you get to the next leader
                while (labelsQueue.size() > 0 && !tempLeadersSet.contains(labelsQueue.peek())) {

                    // keep adding stmts to the currentBBStmtList
                    String label = labelsQueue.pop();
                    LlStatement stmt = labelStmtsMap.get(label);
                    bbLabelsToStmtsMap.put(label, stmt);
                }

                // create the actual BasicBlock and it to the LinkedHashMap
                BasicBlock bb = new BasicBlock(bbLabelsToStmtsMap);
                leadersToBBMap.put(leaderLabel, bb);

            } while (labelsQueue.size() > 0);

            // 3) appropriately connect the basic blocks
            for (int i = 0; i < orderedLeadersList.size(); i++) { // loop through the leaders in the order of the linear order of the basic blocks
                String leaderLabel = orderedLeadersList.get(i);
                BasicBlock bb = leadersToBBMap.get(leaderLabel);

                List<LlStatement> bbStmtsList = bb.getStmtsList();
                LlStatement lastStmtOfCurrentBB = bbStmtsList.get(bbStmtsList.size() - 1);

                // connect if there is a jump from the end of B to the beginning of C
                if (lastStmtOfCurrentBB instanceof LlJump) {
                    String targetLabel = ((LlJump) lastStmtOfCurrentBB).getJumpToLabel();
                    BasicBlock targetBB = leadersToBBMap.get(targetLabel);
                    bb.setAlternativeBranch(targetBB);
                }

                // C immediately follows B and B does not end in an unconditional jump
                // (this only holds if B is not the last block))
                if (!(lastStmtOfCurrentBB instanceof LlJumpUnconditional) && (i < orderedLeadersList.size() - 1)) {
                    String nextBBLeaderLabel = orderedLeadersList.get(i + 1);
                    BasicBlock nextBB = leadersToBBMap.get(nextBBLeaderLabel);
                    bb.setDefaultBranch(nextBB);
                }
            }

            // 4) assign the list of basic blocks as a field of THIS object
            ArrayList<BasicBlock> basicBlocks = new ArrayList<>();
            for (String leaderLabel : orderedLeadersList) {
                basicBlocks.add(leadersToBBMap.get(leaderLabel));
            }
            this.blockLabels = new LinkedHashMap<>(reverse(leadersToBBMap));
            this.basicBlocks = basicBlocks;
        }
    }

    private static HashMap<BasicBlock, String> reverse(Map<String, BasicBlock> map) {
        HashMap<BasicBlock, String> rev = new HashMap<BasicBlock, String>();
        for(Map.Entry<String, BasicBlock> entry : map.entrySet())
            rev.put(entry.getValue(), entry.getKey());
        return rev;
    }

    public BasicBlock getRootBasicBlock() {
        return this.basicBlocks.get(0);
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
}
