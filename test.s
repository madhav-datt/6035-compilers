     .comm  a, 80, 64
     .globl main

.STR_0:
     .string "HHIIH"
.STR_1:
     .string "%d\n"
.STR_2:
     .string "hi\n"
     
main:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*4), %rsp
     movq   %r13, -8(%rbp)
     movq   %r14, -16(%rbp)
     movq   %r12, -24(%rbp)
     # generating code for #_t0 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for i = #_t0
     movq   %r13, %r12

main_WHILE_COND_L2:
     # generating code for #_t1 = 10
     # generating code for 10

     movq   $10, %r14

     # generating code for #_t2 = i < #_t1
     movq   %r14, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t2 goto WHILE_L3
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_WHILE_L3

     # generating code for goto END_WHILE_L3
     jmp    main_END_WHILE_L3

main_WHILE_L3:
     # generating code for #_t4 = printf(#str_t3,)
     movq   $.STR_2, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r13
     # generating code for #_t5 = 1
     # generating code for 1

     movq   $1, %r14

     # generating code for #_t6 = i + #_t5
     movq   %r14, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for i = #_t6
     movq   %r13, %r12

     # generating code for #_t8 = printf(#str_t7,i,)
     movq   $.STR_1, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, %r13
     # generating code for goto WHILE_COND_L2
     jmp    main_WHILE_COND_L2

main_END_WHILE_L3:
     # generating code for #_t10 = printf(#str_t9,)
     movq   $.STR_0, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     movq   -8(%rbp), %r13
     movq   -16(%rbp), %r14
     movq   -24(%rbp), %r12
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

