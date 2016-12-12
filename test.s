     .globl main

.STR_0:
     .string "result should be 46, is: %d\n"
.STR_1:
     .string "-,+ assoc: result should be 90, is: %d\n"
.STR_2:
     .string "*, -, uses var: result should be 11, is: %d\n"
.STR_3:
     .string "paren assoc: result should be 100, is: %d\n"
.STR_4:
     .string "*,- assoc: result should be 2, is: %d\n"
.STR_5:
     .string "- * assoc: result should be 80, is: %d\n"
.STR_6:
     .string "min int operation: result should be -2147483648, is: %d\n"
.STR_7:
     .string "- assoc: result should be 10, is: %d\n"

main:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*17), %rsp
     movq   %r13, -32(%rbp)
     movq   %r14, -24(%rbp)
     movq   %r12, -16(%rbp)
     movq   %rbx, -8(%rbp)
     movq   %r15, 0(%rbp)
     # generating code for #_t0 = 2147483647
     # generating code for 2147483647

     movq   $2147483647, %r13
     movq   $2147483647, %r10
     movq   %r10, %r13

     # generating code for #_t1 = - #_t0

     # generating code for #_t2 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -16(%rbp)
     movq   $1, %r10
     movq   %r10, -16(%rbp)

     # generating code for #_t3 = #_t1 - #_t2
     movq   -16(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r13

     # generating code for i1 = #_t3
     movq   %r13, %r12
     movq   %r13, %r10
     movq   %r10, %r12

     # generating code for #_t5 = printf(#str_t4,i1,)
     movq   $.STR_6, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -24(%rbp)

     # generating code for #_t6 = 100
     # generating code for 100

     movq   $100, %r12
     movq   $100, %r10
     movq   %r10, %r12

     # generating code for #_t7 = 50
     # generating code for 50

     movq   $50, %r10
     movq   %r10, -32(%rbp)
     movq   $50, %r10
     movq   %r10, -32(%rbp)

     # generating code for #_t8 = #_t6 - #_t7
     movq   -32(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r13

     # generating code for #_t9 = 2
     # generating code for 2

     movq   $2, %r12
     movq   $2, %r10
     movq   %r10, %r12

     # generating code for #_t10 = #_t8 * #_t9
     movq   %r12, %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14

     # generating code for i1 = #_t10
     movq   %r14, %r12
     movq   %r14, %r10
     movq   %r10, %r12

     # generating code for #_t12 = printf(#str_t11,i1,)
     movq   $.STR_3, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -40(%rbp)

     # generating code for #_t13 = 100
     # generating code for 100

     movq   $100, %r12
     movq   $100, %r10
     movq   %r10, %r12

     # generating code for #_t14 = 50
     # generating code for 50

     movq   $50, %r10
     movq   %r10, -48(%rbp)
     movq   $50, %r10
     movq   %r10, -48(%rbp)

     # generating code for #_t15 = #_t13 - #_t14
     movq   -48(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r13

     # generating code for #_t16 = 40
     # generating code for 40

     movq   $40, %r12
     movq   $40, %r10
     movq   %r10, %r12

     # generating code for #_t17 = #_t15 - #_t16
     movq   %r12, %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r14

     # generating code for i1 = #_t17
     movq   %r14, %r12
     movq   %r14, %r10
     movq   %r10, %r12

     # generating code for #_t19 = printf(#str_t18,i1,)
     movq   $.STR_7, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -56(%rbp)

     # generating code for #_t20 = 100
     # generating code for 100

     movq   $100, %r13
     movq   $100, %r10
     movq   %r10, %r13

     # generating code for #_t21 = 50
     # generating code for 50

     movq   $50, %r14
     movq   $50, %r10
     movq   %r10, %r14

     # generating code for #_t22 = #_t20 - #_t21
     movq   %r14, %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r12

     # generating code for #_t23 = 40
     # generating code for 40

     movq   $40, %r14
     movq   $40, %r10
     movq   %r10, %r14

     # generating code for #_t24 = #_t22 + #_t23
     movq   %r14, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for i1 = #_t24
     movq   %r13, %r12
     movq   %r13, %r10
     movq   %r10, %r12

     # generating code for #_t26 = printf(#str_t25,i1,)
     movq   $.STR_1, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -64(%rbp)

     # generating code for #_t27 = 100
     # generating code for 100

     movq   $100, %r12
     movq   $100, %r10
     movq   %r10, %r12

     # generating code for #_t28 = 5
     # generating code for 5

     movq   $5, %r12
     movq   $5, %r10
     movq   %r10, %r12

     # generating code for #_t29 = 4
     # generating code for 4

     movq   $4, %r13
     movq   $4, %r10
     movq   %r10, %r13

     # generating code for #_t30 = #_t28 * #_t29
     movq   %r13, %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx

     # generating code for #_t31 = #_t27 - #_t30
     movq   %rbx, %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r13

     # generating code for i1 = #_t31
     movq   %r13, %r12
     movq   %r13, %r10
     movq   %r10, %r12

     # generating code for #_t33 = printf(#str_t32,i1,)
     movq   $.STR_5, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -72(%rbp)

     # generating code for #_t34 = 10
     # generating code for 10

     movq   $10, %r13
     movq   $10, %r10
     movq   %r10, %r13

     # generating code for #_t35 = 5
     # generating code for 5

     movq   $5, %r15
     movq   $5, %r10
     movq   %r10, %r15

     # generating code for #_t36 = #_t34 * #_t35
     movq   %r15, %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r12

     # generating code for #_t37 = 4
     # generating code for 4

     movq   $4, %rbx
     movq   $4, %r10
     movq   %r10, %rbx

     # generating code for #_t38 = #_t36 - #_t37
     movq   %rbx, %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r13

     # generating code for i1 = #_t38
     movq   %r13, %r12
     movq   %r13, %r10
     movq   %r10, %r12

     # generating code for #_t40 = printf(#str_t39,i1,)
     movq   $.STR_0, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -80(%rbp)

     # generating code for #_t41 = 3
     # generating code for 3

     movq   $3, %r13
     movq   $3, %r10
     movq   %r10, %r13

     # generating code for i1 = #_t41
     movq   %r13, %r12
     movq   %r13, %r10
     movq   %r10, %r12

     # generating code for #_t42 = i1 * i1
     movq   %r12, %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r13

     # generating code for #_t43 = 0
     # generating code for 0

     movq   $0, %r12
     movq   $0, %r10
     movq   %r10, %r12

     # generating code for #_t44 = 2
     # generating code for 2

     movq   $2, %rbx
     movq   $2, %r10
     movq   %r10, %rbx

     # generating code for #_t45 = #_t43 - #_t44
     movq   %rbx, %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -88(%rbp)

     # generating code for #_t46 = #_t42 - #_t45
     movq   -88(%rbp), %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r14

     # generating code for i1 = #_t46
     movq   %r14, %r12
     movq   %r14, %r10
     movq   %r10, %r12

     # generating code for #_t48 = printf(#str_t47,i1,)
     movq   $.STR_2, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -96(%rbp)

     # generating code for #_t49 = 3
     # generating code for 3

     movq   $3, %r12
     movq   $3, %r10
     movq   %r10, %r12

     # generating code for #_t50 = 4
     # generating code for 4

     movq   $4, %r10
     movq   %r10, -104(%rbp)
     movq   $4, %r10
     movq   %r10, -104(%rbp)

     # generating code for #_t51 = #_t49 * #_t50
     movq   -104(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r13

     # generating code for #_t52 = 2
     # generating code for 2

     movq   $2, %r12
     movq   $2, %r10
     movq   %r10, %r12

     # generating code for #_t53 = 5
     # generating code for 5

     movq   $5, %r10
     movq   %r10, -112(%rbp)
     movq   $5, %r10
     movq   %r10, -112(%rbp)

     # generating code for #_t54 = #_t52 * #_t53
     movq   -112(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -120(%rbp)

     # generating code for #_t55 = #_t51 - #_t54
     movq   -120(%rbp), %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r14

     # generating code for i1 = #_t55
     movq   %r14, %r12
     movq   %r14, %r10
     movq   %r10, %r12

     # generating code for #_t57 = printf(#str_t56,i1,)
     movq   $.STR_4, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -128(%rbp)

     movq   -32(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -16(%rbp), %r12
     movq   -8(%rbp), %rbx
     movq   0(%rbp), %r15
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc

