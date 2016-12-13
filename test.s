     .globl main

.STR_0:
     .string "\n"
.STR_1:
     .string "["
.STR_2:
     .string "should be here\n"
.STR_3:
     .string "]"
.STR_4:
     .string "should not be here\n"
.STR_5:
     .string "should not be here\n"
.STR_6:
     .string "should be here\n"
.STR_7:
     .string "*"
.STR_8:
     .string "d"
.STR_9:
     .string "o"
.STR_10:
     .string "l"
.STR_11:
     .string "*"
.STR_12:
     .string " "
.STR_13:
     .string "*"
.STR_14:
     .string "*"
.STR_15:
     .string "*"
.STR_16:
     .string "r"
.STR_17:
     .string "*"
.STR_18:
     .string "o"
.STR_19:
     .string "l"
.STR_20:
     .string "W"
.STR_21:
     .string "l"
.STR_22:
     .string "e"
.STR_23:
     .string "H"
.STR_24:
     .string "*"

main:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*12), %rsp
     movq   %r12, -8(%rbp)
     movq   %r13, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %r15, -32(%rbp)
     movq   %rbx, -40(%rbp)
     # generating code for #_t1 = printf(#str_t0,)
     movq   $.STR_1, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     # generating code for #_t2 = true
     # generating code for true

     movq   $1, %r13

     # generating code for #_t3 = true
     # generating code for true

     movq   $1, %r12

     # generating code for #_t4 = true
     # generating code for true

     movq   $1, %r15

     # generating code for #_t5 = true
     # generating code for true

     movq   $1, %r14

     # generating code for #_t6 = false
     # generating code for false

     movq   $0, %r10
     movq   %r10, -48(%rbp)

     # generating code for #_t7 = true
     # generating code for true

     movq   $1, %rbx

     # generating code for #_t8 = true
     # generating code for true

     movq   $1, %r10
     movq   %r10, -56(%rbp)

     # generating code for #_t9 = false
     # generating code for false

     movq   $0, %r10
     movq   %r10, -64(%rbp)

     # generating code for #_t10 = true
     # generating code for true

     movq   $1, %r10
     movq   %r10, -72(%rbp)

     # generating code for #_t11 = true
     # generating code for true

     movq   $1, %r10
     movq   %r10, -80(%rbp)

     # generating code for #_t12 = phw(#_t2,#_t3,#_t4,#_t5,#_t6,#_t7,#_t8,#_t9,#_t10,#_t11,)
     movq   %r13, %rdi
     movq   %r12, %rsi
     movq   %r15, %rdx
     movq   %r14, %rcx
     movq   -48(%rbp), %r8
     movq   %rbx, %r9
     push   -80(%rbp)
     push   -72(%rbp)
     push   -64(%rbp)
     push   -56(%rbp)
     movq   $0, %rax
     call   phw
     movq   %rax, %r10
     movq   %r10, -88(%rbp)
     # generating code for #_t14 = printf(#str_t13,)
     movq   $.STR_3, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     # generating code for #_t16 = printf(#str_t15,)
     movq   $.STR_0, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     # generating code for #_t17 = 3
     # generating code for 3

     movq   $3, %r13

     # generating code for #_t18 = 5
     # generating code for 5

     movq   $5, %r14

     # generating code for #_t19 = #_t17 - #_t18
     movq   %r14, %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r12

     # generating code for #_t20 = 2
     # generating code for 2

     movq   $2, %r13

     # generating code for #_t21 = #_t19 * #_t20
     movq   %r13, %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r14

     # generating code for #_t22 = 2
     # generating code for 2

     movq   $2, %r12

     # generating code for #_t23 = #_t22 > #_t21
     movq   %r14, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovg  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t23 goto IF_L14
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L14

     # generating code for goto ELSE_IF_L15
     jmp    main_ELSE_IF_L15

main_ELSE_IF_L15:
     # generating code for #_t25 = printf(#str_t24,)
     movq   $.STR_4, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     # generating code for goto END_IF_L14
     jmp    main_END_IF_L14

main_IF_L14:
     # generating code for #_t27 = printf(#str_t26,)
     movq   $.STR_2, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     # generating code for #_t28 = 5
     # generating code for 5

     movq   $5, %r14

     # generating code for #_t29 = 2
     # generating code for 2

     movq   $2, %r12

     # generating code for #_t30 = #_t29 > #_t28
     movq   %r14, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovg  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t30 goto IF_L29
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L29

     # generating code for goto ELSE_IF_L30
     jmp    main_ELSE_IF_L30

main_ELSE_IF_L30:
     # generating code for #_t32 = printf(#str_t31,)
     movq   $.STR_6, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     # generating code for goto END_IF_L29
     jmp    main_END_IF_L29

main_IF_L29:
     # generating code for #_t34 = printf(#str_t33,)
     movq   $.STR_5, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
main_END_IF_L29:
main_END_IF_L14:
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %r15
     movq   -40(%rbp), %rbx
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc


phw:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*31), %rsp
     movq   %r12, -8(%rbp)
     movq   %r13, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %r15, -32(%rbp)
     movq   %rbx, -40(%rbp)
     movq   %rdi, %r12
     movq   %rsi, %r13
     movq   %rdx, %r14
     movq   %rcx, %r15
     movq   %r8, %rbx
     movq   %r9, -48(%rbp)
     movq   16(%rbp), %r10
     movq   %r10, -56(%rbp)
     movq   24(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   32(%rbp), %r10
     movq   %r10, -72(%rbp)
     movq   40(%rbp), %r10
     movq   %r10, -80(%rbp)
     # generating code for if a goto IF_L0
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L0

     # generating code for goto END_IF_L0
     jmp    phw_END_IF_L0

phw_IF_L0:
     # generating code for #_t1 = printf(#str_t0,)
     movq   $.STR_23, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -88(%rbp)
     # generating code for if b goto IF_L4
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L4

     # generating code for goto END_IF_L4
     jmp    phw_END_IF_L4

phw_IF_L4:
     # generating code for #_t3 = printf(#str_t2,)
     movq   $.STR_22, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -96(%rbp)
     # generating code for if c goto IF_L8
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L8

     # generating code for goto END_IF_L8
     jmp    phw_END_IF_L8

phw_IF_L8:
     # generating code for #_t5 = printf(#str_t4,)
     movq   $.STR_21, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -104(%rbp)
     # generating code for if d goto IF_L12
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L12

     # generating code for goto END_IF_L12
     jmp    phw_END_IF_L12

phw_IF_L12:
     # generating code for #_t7 = printf(#str_t6,)
     movq   $.STR_19, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -112(%rbp)
     # generating code for if e goto IF_L16
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L16

     # generating code for goto END_IF_L16
     jmp    phw_END_IF_L16

phw_IF_L16:
     # generating code for #_t9 = printf(#str_t8,)
     movq   $.STR_17, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -120(%rbp)
phw_END_IF_L16:
     # generating code for #_t10 = not2(e,)
     movq   %rbx, %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r10
     movq   %r10, -128(%rbp)
     # generating code for if #_t10 goto IF_L20
     movq   -128(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L20

     # generating code for goto END_IF_L20
     jmp    phw_END_IF_L20

phw_IF_L20:
     # generating code for #_t12 = printf(#str_t11,)
     movq   $.STR_9, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -136(%rbp)
phw_END_IF_L20:
phw_END_IF_L12:
     # generating code for #_t13 = not2(d,)
     movq   %r15, %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r12
     # generating code for if #_t13 goto IF_L25
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L25

     # generating code for goto END_IF_L25
     jmp    phw_END_IF_L25

phw_IF_L25:
     # generating code for #_t15 = printf(#str_t14,)
     movq   $.STR_7, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -144(%rbp)
phw_END_IF_L25:
phw_END_IF_L8:
     # generating code for #_t16 = not2(c,)
     movq   %r14, %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r10
     movq   %r10, -152(%rbp)
     # generating code for if #_t16 goto IF_L30
     movq   -152(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L30

     # generating code for goto END_IF_L30
     jmp    phw_END_IF_L30

phw_IF_L30:
     # generating code for #_t18 = printf(#str_t17,)
     movq   $.STR_24, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -160(%rbp)
phw_END_IF_L30:
phw_END_IF_L4:
     # generating code for #_t19 = not2(b,)
     movq   %r13, %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r12
     # generating code for if #_t19 goto IF_L35
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L35

     # generating code for goto END_IF_L35
     jmp    phw_END_IF_L35

phw_IF_L35:
     # generating code for #_t21 = printf(#str_t20,)
     movq   $.STR_14, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -168(%rbp)
phw_END_IF_L35:
phw_END_IF_L0:
     # generating code for #_t22 = not2(a,)
     movq   %r12, %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r12
     # generating code for if #_t22 goto IF_L40
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L40

     # generating code for goto END_IF_L40
     jmp    phw_END_IF_L40

phw_IF_L40:
     # generating code for #_t24 = printf(#str_t23,)
     movq   $.STR_13, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -176(%rbp)
phw_END_IF_L40:
     # generating code for #_t26 = printf(#str_t25,)
     movq   $.STR_12, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -184(%rbp)
     # generating code for #_t27 = not2(f,)
     movq   -48(%rbp), %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r12
     # generating code for if #_t27 goto IF_L46
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L46

     # generating code for goto END_IF_L46
     jmp    phw_END_IF_L46

phw_IF_L46:
     # generating code for #_t29 = printf(#str_t28,)
     movq   $.STR_11, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -192(%rbp)
phw_END_IF_L46:
     # generating code for if g goto IF_L51
     movq   -56(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L51

     # generating code for goto END_IF_L51
     jmp    phw_END_IF_L51

phw_IF_L51:
     # generating code for #_t30 = not2(h,)
     movq   -64(%rbp), %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r12
     # generating code for if #_t30 goto IF_L54
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L54

     # generating code for goto END_IF_L54
     jmp    phw_END_IF_L54

phw_IF_L54:
     # generating code for #_t32 = printf(#str_t31,)
     movq   $.STR_20, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -200(%rbp)
     # generating code for if i goto IF_L59
     movq   -72(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L59

     # generating code for goto END_IF_L59
     jmp    phw_END_IF_L59

phw_IF_L59:
     # generating code for #_t34 = printf(#str_t33,)
     movq   $.STR_18, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -208(%rbp)
     # generating code for if j goto IF_L63
     movq   -80(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L63

     # generating code for goto END_IF_L63
     jmp    phw_END_IF_L63

phw_IF_L63:
     # generating code for #_t36 = printf(#str_t35,)
     movq   $.STR_16, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -216(%rbp)
phw_END_IF_L63:
phw_END_IF_L59:
phw_END_IF_L54:
phw_END_IF_L51:
     # generating code for #_t37 = not2(g,)
     movq   -56(%rbp), %rdi
     movq   $0, %rax
     call   not2
     movq   %rax, %r12
     # generating code for if #_t37 goto IF_L67
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    phw_IF_L67

     # generating code for goto END_IF_L67
     jmp    phw_END_IF_L67

phw_IF_L67:
     # generating code for #_t39 = printf(#str_t38,)
     movq   $.STR_15, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -224(%rbp)
phw_END_IF_L67:
     # generating code for #_t41 = printf(#str_t40,)
     movq   $.STR_10, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -232(%rbp)
     # generating code for #_t43 = printf(#str_t42,)
     movq   $.STR_8, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -240(%rbp)
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %r15
     movq   -40(%rbp), %rbx
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc


not2:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*3), %rsp
     movq   %r12, -8(%rbp)
     movq   %r13, -16(%rbp)
     movq   %rdi, %r12
     # generating code for if a goto IF_L0
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    not2_IF_L0

     # generating code for goto ELSE_IF_L1
     jmp    not2_ELSE_IF_L1

not2_ELSE_IF_L1:
     # generating code for #_t0 = true
     # generating code for true

     movq   $1, %r13

     # generating code for b = #_t0
     movq   %r13, %r12

     # generating code for goto END_IF_L0
     jmp    not2_END_IF_L0

not2_IF_L0:
     # generating code for #_t1 = false
     # generating code for false

     movq   $0, %r13

     # generating code for b = #_t1
     movq   %r13, %r12

not2_END_IF_L0:
     movq   %r12, %rax
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc

