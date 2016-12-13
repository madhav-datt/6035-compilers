     .globl main

.STR_0:
     .string "sum of 1 - 8 is %d (36)\n"
.STR_1:
     .string "correct\n"
.STR_2:
     .string "INCORRECT\n"

main:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*10), %rsp
     movq   %r12, -8(%rbp)
     movq   %r13, 0(%rbp)
     movq   %r14, 8(%rbp)
     movq   %r15, 16(%rbp)
     movq   %rbx, 24(%rbp)
     # generating code for #_t0 = 1
     # generating code for 1

     movq   $1, %r12

     # generating code for a = #_t0
     movq   %r12, %r13

     # generating code for #_t1 = 2
     # generating code for 2

     movq   $2, %r14

     # generating code for b = #_t1
     movq   %r14, %r12

     # generating code for #_t2 = add(a,b,)
     movq   %r13, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   add
     movq   %rax, -8(%rbp)

     # generating code for c = #_t2
     movq   %r15, %r14

     # generating code for #_t3 = sub(c,b,)
     movq   %r14, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   sub
     movq   %rax, -16(%rbp)

     # generating code for d = #_t3
     movq   %r15, %r12

     # generating code for #_t4 = d == a
     movq   %r13, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r14

     # generating code for if #_t4 goto IF_L8
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L8

     # generating code for goto ELSE_IF_L9
     jmp    main_ELSE_IF_L9

main_ELSE_IF_L9:
     # generating code for #_t6 = printf(#str_t5,)
     movq   $.STR_2, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, -24(%rbp)

     # generating code for goto END_IF_L8
     jmp    main_END_IF_L8

main_IF_L8:
     # generating code for #_t8 = printf(#str_t7,)
     movq   $.STR_1, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, -32(%rbp)

main_END_IF_L8:
     # generating code for #_t10 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -40(%rbp)

     # generating code for #_t11 = 2
     # generating code for 2

     movq   $2, %r14

     # generating code for #_t12 = 3
     # generating code for 3

     movq   $3, %r15

     # generating code for #_t13 = 4
     # generating code for 4

     movq   $4, %r12

     # generating code for #_t14 = 5
     # generating code for 5

     movq   $5, %r13

     # generating code for #_t15 = 6
     # generating code for 6

     movq   $6, %r10
     movq   %r10, -48(%rbp)

     # generating code for #_t16 = 7
     # generating code for 7

     movq   $7, %r10
     movq   %r10, -56(%rbp)

     # generating code for #_t17 = 8
     # generating code for 8

     movq   $8, %rbx

     # generating code for #_t18 = sum(#_t10,#_t11,#_t12,#_t13,#_t14,#_t15,#_t16,#_t17,)
     movq   -40(%rbp), %rdi
     movq   %r14, %rsi
     movq   %r15, %rdx
     movq   %r12, %rcx
     movq   %r13, %r8
     movq   -48(%rbp), %r9
     push   %rbx
     push   -56(%rbp)
     movq   $0, %rax
     call   sum
     movq   %rax, -64(%rbp)

     # generating code for #_t19 = printf(#str_t9,#_t18,)
     movq   $.STR_0, %rdi
     movq   -64(%rbp), %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, -72(%rbp)

     movq   -8(%rbp), %r12
     movq   0(%rbp), %r13
     movq   8(%rbp), %r14
     movq   16(%rbp), %r15
     movq   24(%rbp), %rbx
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc


sum:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*9), %rsp
     movq   %r12, -8(%rbp)
     movq   %r13, 0(%rbp)
     movq   %rdi, -8(%rbp)
     movq   %rsi, -16(%rbp)
     movq   %rdx, -24(%rbp)
     movq   %rcx, -32(%rbp)
     movq   %r8, -40(%rbp)
     movq   %r9, -48(%rbp)
     movq   16(%rbp), %r10
     movq   %r10, -56(%rbp)
     movq   24(%rbp), %r10
     movq   %r10, -64(%rbp)
     # generating code for #_t0 = a + b
     movq   -16(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for #_t1 = #_t0 + c
     movq   -24(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for #_t2 = #_t1 + d
     movq   -32(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for #_t3 = #_t2 + e
     movq   -40(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for #_t4 = #_t3 + f
     movq   -48(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for #_t5 = #_t4 + g
     movq   -56(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for #_t6 = #_t5 + h
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     movq   %r13, %rax
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc

     movq   -8(%rbp), %r12
     movq   0(%rbp), %r13

sub:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*3), %rsp
     movq   %r12, -8(%rbp)
     movq   %rdi, -8(%rbp)
     movq   %rsi, -16(%rbp)
     # generating code for #_t0 = a - b
     movq   -16(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r12

     movq   %r12, %rax
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc

     movq   -8(%rbp), %r12

add:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*3), %rsp
     movq   %r12, -8(%rbp)
     movq   %rdi, -8(%rbp)
     movq   %rsi, -16(%rbp)
     # generating code for #_t0 = a + b
     movq   -16(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     movq   %r12, %rax
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc

     movq   -8(%rbp), %r12

