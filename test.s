     .globl main

.STR_0:
     .string "%d\n"
     
main:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*4), %rsp
     movq   %r13, -8(%rbp)
     movq   %r12, -16(%rbp)
     movq   %r14, -24(%rbp)
main_entry:
     # generating code for #_t0 = foo()
     movq   $0, %rax
     call   foo
     movq   %rax, %r12
     # generating code for c = #_t0
     movq   %r12, %r13

     # generating code for #_t2 = printf(#str_t1,#_t0,)
     movq   $.STR_0, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, %r14
main_exit:
     movq   -8(%rbp), %r13
     movq   -16(%rbp), %r12
     movq   -24(%rbp), %r14
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

     
foo:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*11), %rsp
     movq   %r14, -8(%rbp)
     movq   %rbx, -16(%rbp)
     movq   %r12, -24(%rbp)
     movq   %r15, -32(%rbp)
     movq   %r13, -40(%rbp)
foo_entry:
     # generating code for #_t0 = 7
     # generating code for 7

     movq   $7, %r12

     # generating code for #_t1 = get_int(7,)
     # generating code for 7

     movq   $7, %rdi
     movq   $0, %rax
     call   get_int
     movq   %rax, %r13
     # generating code for a = #_t1
     movq   %r13, %r14

     # generating code for #_t2 = 2
     # generating code for 2

     movq   $2, %r15

     # generating code for #_t3 = get_int(2,)
     # generating code for 2

     movq   $2, %rdi
     movq   $0, %rax
     call   get_int
     movq   %rax, %rbx
     # generating code for b = #_t3
     movq   %rbx, %r12

     # generating code for #_t4 = true
     # generating code for true

     movq   $1, %r10
     movq   %r10, -48(%rbp)

     # generating code for #_t5 = get_bool(true,)
     # generating code for true

     movq   $1, %rdi
     movq   $0, %rax
     call   get_bool
     movq   %rax, %r12
     # generating code for x = #_t5
     movq   %r12, %r10
     movq   %r10, -56(%rbp)

     # generating code for #_t6 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -64(%rbp)

     # generating code for c = 0
     # generating code for 0

     movq   $0, %r15

     # generating code for #_t7 = #_t1 * #_t3
     movq   %rbx, %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r12

     # generating code for d = #_t7
     movq   %r12, %r10
     movq   %r10, -72(%rbp)

     # generating code for #_t8 = #_t7
     movq   %r12, %r10
     movq   %r10, -80(%rbp)

     # generating code for a = #_t7
     movq   %r12, %r14

     # generating code for #_t9 = #_t7 + #_t7
     movq   %r12, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r14

     # generating code for c = #_t9
     movq   %r14, %r15

     # generating code for #_t10 = #_t9 + #_t7
     movq   %r12, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r15

     # generating code for #_t11 = #_t10 + #_t7
     movq   %r12, %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %rbx

     movq   %rbx, %rax
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %rbx
     movq   -24(%rbp), %r12
     movq   -32(%rbp), %r15
     movq   -40(%rbp), %r13
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %rbx
     movq   -24(%rbp), %r12
     movq   -32(%rbp), %r15
     movq   -40(%rbp), %r13
     leave   
     ret     

foo_exit:
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %rbx
     movq   -24(%rbp), %r12
     movq   -32(%rbp), %r15
     movq   -40(%rbp), %r13
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

     
get_bool:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*2), %rsp
     movq   %rdi, -8(%rbp)
get_bool_entry:
     movq   -8(%rbp), %rax
     leave   
     ret     

get_bool_exit:
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

     
get_int:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*2), %rsp
     movq   %rdi, -8(%rbp)
get_int_entry:
     movq   -8(%rbp), %rax
     leave   
     ret     

get_int_exit:
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

