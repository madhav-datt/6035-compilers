     .comm  x, 8, 8
     .globl main

.STR_0:
     .string "should be 42: %d\n"
     
main:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*4), %rsp
     movq   %r12, -8(%rbp)
     movq   %r13, -16(%rbp)
     movq   %r14, -24(%rbp)
main_entry:
     # generating code for #_t0 = 0
     # generating code for 0

     movq   $0, %r12

     # generating code for x = 0
     # generating code for 0

     movq   $0, %r12
     movq   $0, %r10
     movq   %r10, x(%rip)

     # generating code for #_t1 = foo()
     call   foo
     movq   %rax, %r13
     # generating code for #_t3 = printf(#str_t2,x,)
     movq   $.STR_0, %rdi
     movq   x(%rip), %rsi
     movq   $0, %rax
     call   printf
     movq   %rax, %r14
main_exit:
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
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

     subq   $(8*2), %rsp
     movq   %r12, -8(%rbp)
foo_entry:
     # generating code for #_t0 = 42
     # generating code for 42

     movq   $42, %r12

     # generating code for x = 42
     # generating code for 42

     movq   $42, %r12
     movq   $42, %r10
     movq   %r10, x(%rip)

foo_exit:
     movq   -8(%rbp), %r12
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

