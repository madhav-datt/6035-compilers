     .comm  A, 8000000, 64
     .globl main
     
f:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*9), %rsp
     movq   %r13, -16(%rbp)
     movq   %r12, -24(%rbp)
     movq   %r14, -32(%rbp)
     movq   %r15, -40(%rbp)
     movq   %rdi, -48(%rbp)
f_entry:
     movq   $0, %r12
     movq   $0, %r12
f_FOR_COND_L4:
     movq   $1000000, %r13
     movq   $1000000, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    f_FOR_L9
     jmp    f_END_FOR_L21
f_FOR_L9:
     movq   -48(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $2, %r14
     movq   %r13, %rax
     movq   A( ,%rax, 8), %rax
     movq   %rax, -56(%rbp)
     movq   $2, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   %r13, %r15
     movq   %r13, %r10
     movq   %r14, %r11
     movq   %r11, A(, %r10, 8)
     movq   $1, %r15
     movq   $8, %rbx
     movq   $8, %r10
     movq   $1, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   %r12, %r11
     addq   %rbx, %r11
     movq   %r11, %r15
     movq   %r15, %r12
     jmp    f_FOR_COND_L4
f_END_FOR_L21:
f_exit:
     movq   -8(%rbp), %rbx
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r12
     movq   -32(%rbp), %r14
     movq   -40(%rbp), %r15
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
