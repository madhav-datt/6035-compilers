     .comm  x, 8000, 64
     .comm  y, 8000, 64
     .globl main

.STR_0:
     .string "\n"
.STR_1:
     .string "%d "

main:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*10), %rsp
     movq   %r13, -32(%rbp)
     movq   %r14, -24(%rbp)
     movq   %r12, -16(%rbp)
     movq   %r15, -8(%rbp)
     movq   %rbx, 0(%rbp)
     # generating code for #_t0 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for j = #_t0
     movq   %r13, %r12

main_FOR_COND_L2:
     # generating code for #_t1 = 1000
     # generating code for 1000

     movq   $1000, %r14

     # generating code for #_t2 = j < #_t1
     movq   %r14, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t2 goto FOR_L3
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_FOR_L3

     # generating code for goto END_FOR_L3
     jmp    main_END_FOR_L3

main_FOR_L3:
     # generating code for x[j]  = j
     # array generating code for x[j]
     movq   %r12, %r10
     # adding something to -8(%rbp)
     movq   %r12, %r11
     movq   %r11, x(, %r10, 8)

     # generating code for #_t3 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for y[j]  = #_t3
     # array generating code for y[j]
     movq   %r12, %r10
     # adding something to -16(%rbp)
     movq   %r13, %r11
     movq   %r11, y(, %r10, 8)

     # generating code for #_t4 = 1
     # generating code for 1

     movq   $1, %r14

     # generating code for #_t5 = j + #_t4
     movq   %r14, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t5
     movq   %r13, %r12

     # generating code for goto FOR_COND_L2
     jmp    main_FOR_COND_L2

main_END_FOR_L3:
     # generating code for #_t6 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for j = #_t6
     movq   %r13, %r12

main_FOR_COND_L17:
     # generating code for #_t7 = 998
     # generating code for 998

     movq   $998, %r14

     # generating code for #_t8 = j < #_t7
     movq   %r14, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t8 goto FOR_L18
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_FOR_L18

     # generating code for goto END_FOR_L18
     jmp    main_END_FOR_L18

main_FOR_L18:
     # generating code for #_t9 = 998
     # generating code for 998

     movq   $998, %r13

     # generating code for #_t10 = #_t9 - j
     movq   %r12, %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r14

     # generating code for i = #_t10
     movq   %r14, %r13

     # generating code for #_t11 = 1
     # generating code for 1

     movq   $1, %r14

     # generating code for #_t12 = i + #_t11
     movq   %r14, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r15

     # generating code for a = x[#_t12]
     # array generating code for x[#_t12]
     movq   %r15, %rax
     movq   x( ,%rax, 8), %rax
     movq   %rax, -24(%rbp)
     movq   -24(%rbp), %r15

     # generating code for b = x[i]
     # array generating code for x[i]
     movq   %r13, %rax
     movq   x( ,%rax, 8), %rax
     movq   %rax, -32(%rbp)
     movq   -32(%rbp), %r14

     # generating code for #_t13 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t14 = i - #_t13
     movq   %r15, %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %rbx

     # generating code for c = x[#_t14]
     # array generating code for x[#_t14]
     movq   %rbx, %rax
     movq   x( ,%rax, 8), %rax
     movq   %rax, -40(%rbp)
     movq   -40(%rbp), %rbx

     # generating code for #_t15 = 2
     # generating code for 2

     movq   $2, %r13

     # generating code for #_t16 = b * #_t15
     movq   %r13, %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r15

     # generating code for d = #_t16
     movq   %r15, %r13

     # generating code for #_t17 = d + a
     movq   %r15, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r14

     # generating code for d = #_t17
     movq   %r14, %r13

     # generating code for #_t18 = d + c
     movq   %rbx, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r14

     # generating code for d = #_t18
     movq   %r14, %r13

     # generating code for y[i]  = d
     # array generating code for y[i]
     movq   %r13, %r10
     # adding something to -48(%rbp)
     movq   %r13, %r11
     movq   %r11, y(, %r10, 8)

     # generating code for #_t19 = 1
     # generating code for 1

     movq   $1, %r14

     # generating code for #_t20 = j + #_t19
     movq   %r14, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t20
     movq   %r13, %r12

     # generating code for goto FOR_COND_L17
     jmp    main_FOR_COND_L17

main_END_FOR_L18:
     # generating code for #_t21 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for j = #_t21
     movq   %r13, %r12

main_FOR_COND_L47:
     # generating code for #_t22 = 1000
     # generating code for 1000

     movq   $1000, %r14

     # generating code for #_t23 = j < #_t22
     movq   %r14, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t23 goto FOR_L48
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_FOR_L48

     # generating code for goto END_FOR_L48
     jmp    main_END_FOR_L48

main_FOR_L48:
     # generating code for #_t25 = printf(#str_t24,y[j] ,)
     movq   $.STR_1, %rdi
     # array generating code for y[j]
     movq   %r12, %rax
     movq   y( ,%rax, 8), %rax
     movq   %rax, -56(%rbp)
     movq   -56(%rbp), %r10
     movq   %r10, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -64(%rbp)

     # generating code for #_t26 = 1
     # generating code for 1

     movq   $1, %r14

     # generating code for #_t27 = j + #_t26
     movq   %r14, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t27
     movq   %r13, %r12

     # generating code for goto FOR_COND_L47
     jmp    main_FOR_COND_L47

main_END_FOR_L48:
     # generating code for #_t29 = printf(#str_t28,)
     movq   $.STR_0, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, -72(%rbp)

     movq   -32(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -16(%rbp), %r12
     movq   -8(%rbp), %r15
     movq   0(%rbp), %rbx
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc

