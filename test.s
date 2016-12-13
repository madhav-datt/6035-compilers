     .globl main

.STR_0:
     .string "j:%d k:%d\n"
     
main:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*5), %rsp
     movq   %r13, -8(%rbp)
     movq   %r12, -16(%rbp)
     movq   %r15, -24(%rbp)
     movq   %r14, -32(%rbp)
     # generating code for #_t0 = 0
     # generating code for 0

     movq   $0, %r12

     # generating code for j = #_t0
     movq   %r12, %r14

main_FOR_COND_L2:
     # generating code for #_t1 = 15
     # generating code for 15

     movq   $15, %r13

     # generating code for #_t2 = j < #_t1
     movq   %r13, %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r12

     # generating code for if #_t2 goto FOR_L3
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_FOR_L3

     # generating code for goto END_FOR_L3
     jmp    main_END_FOR_L3

main_FOR_L3:
     # generating code for #_t3 = 2
     # generating code for 2

     movq   $2, %r13

     # generating code for #_t4 = j == #_t3
     movq   %r13, %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r12

     # generating code for if #_t4 goto IF_L8
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L8

     # generating code for goto END_IF_L8
     jmp    main_END_IF_L8

main_IF_L8:
     # generating code for #_t5 = 1
     # generating code for 1

     movq   $1, %r13

     # generating code for #_t6 = j + #_t5
     movq   %r13, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for j = #_t6
     movq   %r12, %r14

     # generating code for goto FOR_COND_L2
     jmp    main_FOR_COND_L2

main_END_IF_L8:
     # generating code for #_t7 = 4
     # generating code for 4

     movq   $4, %r13

     # generating code for #_t8 = j == #_t7
     movq   %r13, %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r12

     # generating code for if #_t8 goto IF_L17
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L17

     # generating code for goto END_IF_L17
     jmp    main_END_IF_L17

main_IF_L17:
     # generating code for goto END_FOR_L3
     jmp    main_END_FOR_L3

main_END_IF_L17:
     # generating code for #_t9 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for k = #_t9
     movq   %r13, %r12

main_FOR_COND_L25:
     # generating code for #_t10 = 19
     # generating code for 19

     movq   $19, %r15

     # generating code for #_t11 = k < #_t10
     movq   %r15, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t11 goto FOR_L26
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_FOR_L26

     # generating code for goto END_FOR_L26
     jmp    main_END_FOR_L26

main_FOR_L26:
     # generating code for #_t12 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t13 = k == #_t12
     movq   %r15, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t13 goto IF_L31
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L31

     # generating code for goto END_IF_L31
     jmp    main_END_IF_L31

main_IF_L31:
     # generating code for #_t14 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t15 = k + #_t14
     movq   %r15, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for k = #_t15
     movq   %r13, %r12

     # generating code for goto FOR_COND_L25
     jmp    main_FOR_COND_L25

main_END_IF_L31:
     # generating code for #_t16 = 9
     # generating code for 9

     movq   $9, %r15

     # generating code for #_t17 = k == #_t16
     movq   %r15, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t17 goto IF_L40
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L40

     # generating code for goto END_IF_L40
     jmp    main_END_IF_L40

main_IF_L40:
     # generating code for goto END_FOR_L26
     jmp    main_END_FOR_L26

main_END_IF_L40:
     # generating code for #_t19 = printf(#str_t18,j,k,)
     movq   $.STR_0, %rdi
     movq   %r14, %rsi
     movq   %r12, %rdx
     movq   $0, %rax
     call   printf
     movq   %rax, %r13
     # generating code for #_t20 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t21 = k + #_t20
     movq   %r15, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for k = #_t21
     movq   %r13, %r12

     # generating code for goto FOR_COND_L25
     jmp    main_FOR_COND_L25

main_END_FOR_L26:
     # generating code for #_t22 = 1
     # generating code for 1

     movq   $1, %r13

     # generating code for #_t23 = j + #_t22
     movq   %r13, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for j = #_t23
     movq   %r12, %r14

     # generating code for goto FOR_COND_L2
     jmp    main_FOR_COND_L2

main_END_FOR_L3:
     movq   -8(%rbp), %r13
     movq   -16(%rbp), %r12
     movq   -24(%rbp), %r15
     movq   -32(%rbp), %r14
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

