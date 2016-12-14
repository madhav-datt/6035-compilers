     .globl main

.STR_0:
     .string "4INCORRECT\n"
.STR_1:
     .string "correct\n"
.STR_2:
     .string "3INCORRECT\n"
.STR_3:
     .string "correct\n"
.STR_4:
     .string "correct\n"
.STR_5:
     .string "correct\n"
.STR_6:
     .string "2INCORRECT\n"
.STR_7:
     .string "6INCORRECT\n"
.STR_8:
     .string "1INCORRECT\n"
.STR_9:
     .string "should see 6 corrects and no incorrects\n"
.STR_10:
     .string "correct\n"
.STR_11:
     .string "correct\n"
.STR_12:
     .string "5INCORRECT\n"
.STR_13:
     .string "INCORRECT - shorcircuiting failed\n"
     
main:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*23), %rsp
     movq   %rbx, -8(%rbp)
     movq   %r15, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %r12, -32(%rbp)
     movq   %r13, -40(%rbp)
main_entry:
     # generating code for #_t1 = printf(#str_t0,)
     movq   $.STR_9, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     # generating code for #_t2 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for #_t3 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for 0

     # generating code for 0

     # generating code for #_t4 = 0 == 0
     movq   $0, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r13

     # generating code for a = #_t4
     movq   %r13, %r14

     # generating code for #_t5 = 1
     # generating code for 1

     movq   $1, %r14

     # generating code for #_t6 = 0
     # generating code for 0

     movq   $0, %r15

     # generating code for #_t7 = 3
     # generating code for 3

     movq   $3, %r15

     # generating code for 0

     # generating code for 3

     # generating code for #_t8 = 0 - 3
     movq   $3, %r10
     movq   $0, %r11
     subq   %r10, %r11
     movq   %r11, %r12

     # generating code for 1

     # generating code for #_t9 = #_t8 == 1
     movq   $1, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r14

     # generating code for b = #_t9
     movq   %r14, %rbx

     # generating code for #_t56 = ! #_t4
     movq   %r13, %r10
     xorq   $1, %r10
     movq   %r10, %r12

     # generating code for #_t10 = #_t56
     movq   %r12, %r10
     movq   %r10, -48(%rbp)

     # generating code for c = #_t56
     movq   %r12, %r10
     movq   %r10, -56(%rbp)

     # generating code for #_t11 = #_t56
     movq   %r12, %r10
     movq   %r10, -64(%rbp)

     # generating code for #_t12 = false
     # generating code for false

     movq   $0, %r10
     movq   %r10, -72(%rbp)

     # generating code for #_t13 = fail(false,)
     # generating code for false

     movq   $0, %rdi
     call   fail
     movq   %rax, %rbx
     # generating code for #_t14 = #_t56 && #_t13
     movq   %r12, %r11
     and    %rbx, %r11
     movq   %r11, %r15

     # generating code for if #_t14 goto IF_L25
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L25

     # generating code for goto ELSE_IF_L22
     jmp    main_ELSE_IF_L22

main_ELSE_IF_L22:
     # generating code for #_t16 = printf(#str_t15,)
     movq   $.STR_11, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -80(%rbp)
     # generating code for goto END_IF_L27
     jmp    main_END_IF_L27

main_IF_L25:
     # generating code for #_t18 = printf(#str_t17,)
     movq   $.STR_8, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -88(%rbp)
main_END_IF_L27:
     # generating code for #_t19 = ! #_t9
     movq   %r14, %r10
     xorq   $1, %r10
     movq   %r10, %r13

     # generating code for #_t20 = true
     # generating code for true

     movq   $1, %rbx

     # generating code for #_t21 = fail(true,)
     # generating code for true

     movq   $1, %rdi
     call   fail
     movq   %rax, %r10
     movq   %r10, -96(%rbp)
     # generating code for #_t22 = #_t19 || #_t21
     movq   -96(%rbp), %r10
     movq   %r13, %r11
     or     %r10, %r11
     movq   %r11, %r15

     # generating code for if #_t22 goto IF_L37
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L37

     # generating code for goto ELSE_IF_L34
     jmp    main_ELSE_IF_L34

main_ELSE_IF_L34:
     # generating code for #_t24 = printf(#str_t23,)
     movq   $.STR_6, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %rbx
     # generating code for goto END_IF_L39
     jmp    main_END_IF_L39

main_IF_L37:
     # generating code for #_t26 = printf(#str_t25,)
     movq   $.STR_4, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -104(%rbp)
main_END_IF_L39:
     # generating code for if #_t56 goto IF_L45
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L45

     # generating code for goto ELSE_IF_L42
     jmp    main_ELSE_IF_L42

main_ELSE_IF_L42:
     # generating code for #_t28 = printf(#str_t27,)
     movq   $.STR_3, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -112(%rbp)
     # generating code for goto END_IF_L47
     jmp    main_END_IF_L47

main_IF_L45:
     # generating code for #_t30 = printf(#str_t29,)
     movq   $.STR_2, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -120(%rbp)
main_END_IF_L47:
     # generating code for #_t55 = ! #_t56
     movq   %r12, %r10
     xorq   $1, %r10
     movq   %r10, %r13

     # generating code for #_t31 = #_t55
     movq   %r13, %r15

     # generating code for #_t32 = ! #_t55
     movq   %r13, %r10
     xorq   $1, %r10
     movq   %r10, %r14

     # generating code for #_t33 = false
     # generating code for false

     movq   $0, %r10
     movq   %r10, -128(%rbp)

     # generating code for #_t34 = fail(false,)
     # generating code for false

     movq   $0, %rdi
     call   fail
     movq   %rax, %r10
     movq   %r10, -136(%rbp)
     # generating code for #_t35 = #_t32 && #_t34
     movq   -136(%rbp), %r10
     movq   %r14, %r11
     and    %r10, %r11
     movq   %r11, %r15

     # generating code for if #_t35 goto IF_L59
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L59

     # generating code for goto ELSE_IF_L56
     jmp    main_ELSE_IF_L56

main_ELSE_IF_L56:
     # generating code for #_t37 = printf(#str_t36,)
     movq   $.STR_1, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %rbx
     # generating code for goto END_IF_L61
     jmp    main_END_IF_L61

main_IF_L59:
     # generating code for #_t39 = printf(#str_t38,)
     movq   $.STR_0, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %rbx
main_END_IF_L61:
     # generating code for #_t40 = #_t55
     movq   %r13, %r10
     movq   %r10, -144(%rbp)

     # generating code for #_t41 = ! #_t55
     movq   %r13, %r10
     xorq   $1, %r10
     movq   %r10, %r10
     movq   %r10, -152(%rbp)

     # generating code for #_t42 = ! #_t41
     movq   -152(%rbp), %r10
     xorq   $1, %r10
     movq   %r10, %rbx

     # generating code for if #_t42 goto IF_L70
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L70

     # generating code for goto ELSE_IF_L67
     jmp    main_ELSE_IF_L67

main_ELSE_IF_L67:
     # generating code for #_t44 = printf(#str_t43,)
     movq   $.STR_12, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -160(%rbp)
     # generating code for goto END_IF_L72
     jmp    main_END_IF_L72

main_IF_L70:
     # generating code for #_t46 = printf(#str_t45,)
     movq   $.STR_10, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r10
     movq   %r10, -168(%rbp)
main_END_IF_L72:
     # generating code for #_t47 = #_t56
     movq   %r12, %r10
     movq   %r10, -176(%rbp)

     # generating code for #_t48 = ! #_t56
     movq   %r12, %r10
     xorq   $1, %r10
     movq   %r10, %rbx

     # generating code for c = #_t48
     movq   %rbx, %r10
     movq   %r10, -56(%rbp)

     # generating code for #_t49 = #_t56
     movq   %r12, %r14

     # generating code for #_t50 = #_t56 != #_t48
     movq   %r12, %r11
     cmp    %rbx, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovne  %r10, %r11
     movq   %r11, %r14

     # generating code for if #_t50 goto IF_L83
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    main_IF_L83

     # generating code for goto ELSE_IF_L80
     jmp    main_ELSE_IF_L80

main_ELSE_IF_L80:
     # generating code for #_t52 = printf(#str_t51,)
     movq   $.STR_7, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r15
     # generating code for goto END_IF_L85
     jmp    main_END_IF_L85

main_IF_L83:
     # generating code for #_t54 = printf(#str_t53,)
     movq   $.STR_5, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r15
main_END_IF_L85:
main_exit:
     movq   -8(%rbp), %rbx
     movq   -16(%rbp), %r15
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %r12
     movq   -40(%rbp), %r13
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

     
fail:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*3), %rsp
     movq   %r12, -8(%rbp)
     movq   %rdi, -16(%rbp)
fail_entry:
     # generating code for #_t1 = printf(#str_t0,)
     movq   $.STR_13, %rdi
     movq   $0, %rax
     call   printf
     movq   %rax, %r12
     movq   -16(%rbp), %rax
     movq   -8(%rbp), %r12
     movq   -8(%rbp), %r12
     leave   
     ret     

fail_exit:
     movq   -8(%rbp), %r12
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

