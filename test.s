     .comm  unsharpMask, 6000000, 64
     .comm  image, 18400000, 64
     .comm  unsharpKernel, 72, 64
     .comm  rows, 8, 8
     .comm  cols, 8, 8
     .comm  kernel_sum, 8, 8
     .globl main
.STR_0:
     .string "output.ppm"
.STR_1:
     .string "input.ppm"
     
main:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*20), %rsp
     movq   %r13, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %r15, -32(%rbp)
     movq   %rbx, -40(%rbp)
     movq   $0, %rax
     call   read_file
     movq   %rax, %r13
     movq   $0, %rax
     call   start_caliper
     movq   %rax, %r12
     movq   $0, %rax
     call   levels
     movq   %rax, %r10
     movq   %r10, -48(%rbp)
     movq   $0, %rax
     call   convert2HSV
     movq   %rax, %r15
     movq   $0, %rax
     call   createKernel
     movq   %rax, %r10
     movq   %r10, -56(%rbp)
     movq   $0, %rax
     call   createUnsharpMaskH
     movq   %rax, %r10
     movq   %r10, -64(%rbp)
     movq   $4, %rbx
     movq   %rbx, %r10
     neg    %r10
     movq   %r10, %r10
     movq   %r10, -72(%rbp)
     movq   $360, %r10
     movq   %r10, -80(%rbp)
     movq   -72(%rbp), %rdi
     movq   -80(%rbp), %rsi
     movq   $0, %rax
     call   sharpenH
     movq   %rax, %r10
     movq   %r10, -88(%rbp)
     movq   $0, %rax
     call   createUnsharpMaskS
     movq   %rax, %r10
     movq   %r10, -96(%rbp)
     movq   $4, %r13
     movq   %r13, %r10
     neg    %r10
     movq   %r10, %r14
     movq   $1024, %r12
     movq   %r14, %rdi
     movq   %r12, %rsi
     movq   $0, %rax
     call   sharpenS
     movq   %rax, %r10
     movq   %r10, -104(%rbp)
     movq   $0, %rax
     call   createUnsharpMaskV
     movq   %rax, %r10
     movq   %r10, -112(%rbp)
     movq   $4, %r10
     movq   %r10, -120(%rbp)
     movq   -120(%rbp), %r10
     neg    %r10
     movq   %r10, %r15
     movq   $1024, %rbx
     movq   %r15, %rdi
     movq   %rbx, %rsi
     movq   $0, %rax
     call   sharpenV
     movq   %rax, %r10
     movq   %r10, -128(%rbp)
     movq   $0, %rax
     call   convert2RGB
     movq   %rax, %r10
     movq   %r10, -136(%rbp)
     movq   $0, %rax
     call   end_caliper
     movq   %rax, %r10
     movq   %r10, -144(%rbp)
     movq   $0, %rax
     call   write_file
     movq   %rax, %r10
     movq   %r10, -152(%rbp)
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %r15
     movq   -40(%rbp), %rbx
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
levels:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*155), %rsp
     movq   %rbx, -16(%rbp)
     movq   %r12, -24(%rbp)
     movq   %r14, -32(%rbp)
     movq   %r13, -40(%rbp)
     movq   $10, %r12
     movq   %r12, %r10
     movq   %r10, -48(%rbp)
     movq   $243, %r10
     movq   %r10, -56(%rbp)
     movq   -56(%rbp), %r14
     movq   $0, %r10
     movq   %r10, -64(%rbp)
     movq   -64(%rbp), %r13
levels_FOR_COND_L7:
     movq   rows(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_FOR_L11
     jmp    levels_END_FOR_L214
levels_FOR_L11:
     movq   $0, %r15
     movq   %r15, %r12
levels_FOR_COND_L14:
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_FOR_L18
     jmp    levels_END_FOR_L209
levels_FOR_L18:
     movq   $2193, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -88(%rbp)
     movq   $3, %r10
     movq   %r10, -96(%rbp)
     movq   -96(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   -88(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -112(%rbp)
     movq   -112(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -120(%rbp)
     movq   -48(%rbp), %r10
     movq   -120(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %rbx
     movq   $255, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   %rbx, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -48(%rbp), %r10
     movq   %r14, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   %r15, %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -144(%rbp)
     movq   $2193, %r10
     movq   %r10, -152(%rbp)
     movq   -152(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -160(%rbp)
     movq   $3, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   -160(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   -144(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $0, %r10
     movq   %r10, -200(%rbp)
     movq   $2193, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -232(%rbp)
     movq   -232(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -240(%rbp)
     movq   -200(%rbp), %r10
     movq   -240(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_IF_L64
     jmp    levels_ELSE_IF_L44
levels_ELSE_IF_L44:
     movq   $255, %r10
     movq   %r10, -256(%rbp)
     movq   $2193, %r10
     movq   %r10, -264(%rbp)
     movq   -264(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -272(%rbp)
     movq   -272(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -280(%rbp)
     movq   -280(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -288(%rbp)
     movq   -288(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -296(%rbp)
     movq   -256(%rbp), %r10
     movq   -296(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovg  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -304(%rbp)
     movq   -304(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_IF_L54
     jmp    levels_END_IF_L62
levels_IF_L54:
     movq   $255, %r10
     movq   %r10, -312(%rbp)
     movq   $2193, %r10
     movq   %r10, -320(%rbp)
     movq   -320(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -328(%rbp)
     movq   -328(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   -336(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -344(%rbp)
     movq   -344(%rbp), %r10
     movq   -312(%rbp), %r11
     movq   %r11, image(, %r10, 8)
levels_END_IF_L62:
     jmp    levels_END_IF_L72
levels_IF_L64:
     movq   $0, %r10
     movq   %r10, -360(%rbp)
     movq   $2193, %r10
     movq   %r10, -368(%rbp)
     movq   -368(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -376(%rbp)
     movq   -376(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -384(%rbp)
     movq   -384(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -392(%rbp)
     movq   -392(%rbp), %r10
     movq   -360(%rbp), %r11
     movq   %r11, image(, %r10, 8)
levels_END_IF_L72:
     movq   $2193, %r10
     movq   %r10, -408(%rbp)
     movq   -408(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -416(%rbp)
     movq   -416(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -424(%rbp)
     movq   -424(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -432(%rbp)
     movq   $1, %r10
     movq   %r10, -440(%rbp)
     movq   -440(%rbp), %r10
     movq   -432(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -448(%rbp)
     movq   -448(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -456(%rbp)
     movq   -48(%rbp), %r10
     movq   -456(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -464(%rbp)
     movq   $255, %r10
     movq   %r10, -472(%rbp)
     movq   -472(%rbp), %r10
     movq   -464(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -480(%rbp)
     movq   -48(%rbp), %r10
     movq   %r14, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -488(%rbp)
     movq   -488(%rbp), %r10
     movq   -480(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -496(%rbp)
     movq   $2193, %r10
     movq   %r10, -504(%rbp)
     movq   -504(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -512(%rbp)
     movq   -512(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -520(%rbp)
     movq   -520(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -528(%rbp)
     movq   $1, %r10
     movq   %r10, -536(%rbp)
     movq   -536(%rbp), %r10
     movq   -528(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -544(%rbp)
     movq   -544(%rbp), %r10
     movq   -496(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $0, %r10
     movq   %r10, -560(%rbp)
     movq   $2193, %r10
     movq   %r10, -568(%rbp)
     movq   -568(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -576(%rbp)
     movq   -576(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -584(%rbp)
     movq   -584(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -592(%rbp)
     movq   $1, %r10
     movq   %r10, -600(%rbp)
     movq   -600(%rbp), %r10
     movq   -592(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -608(%rbp)
     movq   -608(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -616(%rbp)
     movq   -560(%rbp), %r10
     movq   -616(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -624(%rbp)
     movq   -624(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_IF_L128
     jmp    levels_ELSE_IF_L104
levels_ELSE_IF_L104:
     movq   $255, %r10
     movq   %r10, -632(%rbp)
     movq   $2193, %r10
     movq   %r10, -640(%rbp)
     movq   -640(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -648(%rbp)
     movq   -648(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -656(%rbp)
     movq   $1, %r10
     movq   %r10, -664(%rbp)
     movq   -664(%rbp), %r10
     movq   -656(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -672(%rbp)
     movq   -672(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -680(%rbp)
     movq   -632(%rbp), %r10
     movq   -680(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovg  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -688(%rbp)
     movq   -688(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_IF_L116
     jmp    levels_END_IF_L126
levels_IF_L116:
     movq   $255, %r10
     movq   %r10, -696(%rbp)
     movq   $2193, %r10
     movq   %r10, -704(%rbp)
     movq   -704(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -712(%rbp)
     movq   $3, %r10
     movq   %r10, -720(%rbp)
     movq   -720(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -728(%rbp)
     movq   -728(%rbp), %r10
     movq   -712(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $1, %r10
     movq   %r10, -736(%rbp)
     movq   -736(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -744(%rbp)
     movq   -744(%rbp), %r10
     movq   -696(%rbp), %r11
     movq   %r11, image(, %r10, 8)
levels_END_IF_L126:
     jmp    levels_END_IF_L138
levels_IF_L128:
     movq   $0, %r10
     movq   %r10, -760(%rbp)
     movq   $2193, %r10
     movq   %r10, -768(%rbp)
     movq   -768(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -776(%rbp)
     movq   -776(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -784(%rbp)
     movq   -784(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -792(%rbp)
     movq   $1, %r10
     movq   %r10, -800(%rbp)
     movq   -800(%rbp), %r10
     movq   -792(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -808(%rbp)
     movq   -808(%rbp), %r10
     movq   -760(%rbp), %r11
     movq   %r11, image(, %r10, 8)
levels_END_IF_L138:
     movq   $2193, %r10
     movq   %r10, -824(%rbp)
     movq   -824(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -832(%rbp)
     movq   $3, %r10
     movq   %r10, -840(%rbp)
     movq   -840(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -848(%rbp)
     movq   -848(%rbp), %r10
     movq   -832(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -856(%rbp)
     movq   $2, %r10
     movq   %r10, -864(%rbp)
     movq   -864(%rbp), %r10
     movq   -856(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -872(%rbp)
     movq   -872(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -880(%rbp)
     movq   -48(%rbp), %r10
     movq   -880(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r15
     movq   $255, %r10
     movq   %r10, -888(%rbp)
     movq   -888(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   -48(%rbp), %r10
     movq   %r14, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -896(%rbp)
     movq   -896(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -904(%rbp)
     movq   $2193, %r10
     movq   %r10, -912(%rbp)
     movq   -912(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -920(%rbp)
     movq   $3, %r10
     movq   %r10, -928(%rbp)
     movq   -928(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -936(%rbp)
     movq   -936(%rbp), %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -944(%rbp)
     movq   $2, %r10
     movq   %r10, -952(%rbp)
     movq   -952(%rbp), %r10
     movq   -944(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -960(%rbp)
     movq   -960(%rbp), %r10
     movq   -904(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $0, %r10
     movq   %r10, -976(%rbp)
     movq   $2193, %r15
     movq   %r15, %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -984(%rbp)
     movq   -984(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -992(%rbp)
     movq   -992(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $2, %r10
     movq   %r10, -1000(%rbp)
     movq   -1000(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1008(%rbp)
     movq   -1008(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1016(%rbp)
     movq   -976(%rbp), %r10
     movq   -1016(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1024(%rbp)
     movq   -1024(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_IF_L194
     jmp    levels_ELSE_IF_L170
levels_ELSE_IF_L170:
     movq   $255, %r10
     movq   %r10, -1032(%rbp)
     movq   $2193, %r10
     movq   %r10, -1040(%rbp)
     movq   -1040(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -1048(%rbp)
     movq   -1048(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1056(%rbp)
     movq   -1056(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -1064(%rbp)
     movq   -1064(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1072(%rbp)
     movq   -1072(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1080(%rbp)
     movq   -1032(%rbp), %r10
     movq   -1080(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovg  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1088(%rbp)
     movq   -1088(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    levels_IF_L182
     jmp    levels_END_IF_L192
levels_IF_L182:
     movq   $255, %r10
     movq   %r10, -1096(%rbp)
     movq   $2193, %r10
     movq   %r10, -1104(%rbp)
     movq   -1104(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1112(%rbp)
     movq   $3, %r10
     movq   %r10, -1120(%rbp)
     movq   -1120(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1128(%rbp)
     movq   -1128(%rbp), %r10
     movq   -1112(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $2, %r10
     movq   %r10, -1136(%rbp)
     movq   -1136(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1144(%rbp)
     movq   -1144(%rbp), %r10
     movq   -1096(%rbp), %r11
     movq   %r11, image(, %r10, 8)
levels_END_IF_L192:
     jmp    levels_END_IF_L204
levels_IF_L194:
     movq   $0, %r10
     movq   %r10, -1160(%rbp)
     movq   $2193, %r10
     movq   %r10, -1168(%rbp)
     movq   -1168(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -1176(%rbp)
     movq   -1176(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1184(%rbp)
     movq   -1184(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -1192(%rbp)
     movq   -1192(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1200(%rbp)
     movq   -1200(%rbp), %r10
     movq   -1160(%rbp), %r11
     movq   %r11, image(, %r10, 8)
levels_END_IF_L204:
     movq   $1, %r10
     movq   %r10, -1216(%rbp)
     movq   -1216(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r12
     jmp    levels_FOR_COND_L14
levels_END_FOR_L209:
     movq   $1, %r10
     movq   %r10, -1224(%rbp)
     movq   -1224(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1232(%rbp)
     movq   -1232(%rbp), %r13
     jmp    levels_FOR_COND_L7
levels_END_FOR_L214:
     movq   -8(%rbp), %r15
     movq   -16(%rbp), %rbx
     movq   -24(%rbp), %r12
     movq   -32(%rbp), %r14
     movq   -40(%rbp), %r13
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
sharpenV:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*47), %rsp
     movq   %r13, -16(%rbp)
     movq   %r15, -24(%rbp)
     movq   %rbx, -32(%rbp)
     movq   %r12, -40(%rbp)
     movq   %rdi, -48(%rbp)
     movq   %rsi, -56(%rbp)
     movq   $0, %r14
     movq   %r14, %r13
sharpenV_FOR_COND_L3:
     movq   cols(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -64(%rbp)
     movq   -64(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenV_FOR_L7
     jmp    sharpenV_END_FOR_L68
sharpenV_FOR_L7:
     movq   $0, %r15
     movq   %r15, %r12
sharpenV_FOR_COND_L10:
     movq   rows(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenV_FOR_L14
     jmp    sharpenV_END_FOR_L63
sharpenV_FOR_L14:
     movq   $2193, %rbx
     movq   %rbx, %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -96(%rbp)
     movq   $2, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   -96(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -112(%rbp)
     movq   $731, %r10
     movq   %r10, -120(%rbp)
     movq   -120(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   %r13, %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   -48(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -152(%rbp)
     movq   -112(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -160(%rbp)
     movq   -152(%rbp), %r10
     movq   -160(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   -56(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -168(%rbp)
     movq   $2193, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -184(%rbp)
     movq   $3, %r10
     movq   %r10, -192(%rbp)
     movq   -192(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -200(%rbp)
     movq   -200(%rbp), %r10
     movq   -184(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   -168(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -232(%rbp)
     movq   -232(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -240(%rbp)
     movq   -240(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $2, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -264(%rbp)
     movq   -264(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -272(%rbp)
     movq   -56(%rbp), %r10
     movq   -272(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, %r10
     movq   %r10, -280(%rbp)
     movq   -280(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenV_IF_L47
     jmp    sharpenV_END_IF_L58
sharpenV_IF_L47:
     movq   $1, %r10
     movq   %r10, -288(%rbp)
     movq   -288(%rbp), %r10
     movq   -56(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -296(%rbp)
     movq   $2193, %r10
     movq   %r10, -304(%rbp)
     movq   -304(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -312(%rbp)
     movq   -312(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -320(%rbp)
     movq   -320(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $2, %r10
     movq   %r10, -328(%rbp)
     movq   -328(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   -336(%rbp), %r10
     movq   -296(%rbp), %r11
     movq   %r11, image(, %r10, 8)
sharpenV_END_IF_L58:
     movq   $1, %r10
     movq   %r10, -352(%rbp)
     movq   -352(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r12
     jmp    sharpenV_FOR_COND_L10
sharpenV_END_FOR_L63:
     movq   $1, %r10
     movq   %r10, -360(%rbp)
     movq   -360(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -368(%rbp)
     movq   -368(%rbp), %r13
     jmp    sharpenV_FOR_COND_L3
sharpenV_END_FOR_L68:
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r15
     movq   -32(%rbp), %rbx
     movq   -40(%rbp), %r12
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
sharpenS:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*47), %rsp
     movq   %r13, -16(%rbp)
     movq   %r15, -24(%rbp)
     movq   %rbx, -32(%rbp)
     movq   %r12, -40(%rbp)
     movq   %rdi, -48(%rbp)
     movq   %rsi, -56(%rbp)
     movq   $0, %r14
     movq   %r14, %r13
sharpenS_FOR_COND_L3:
     movq   cols(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -64(%rbp)
     movq   -64(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenS_FOR_L7
     jmp    sharpenS_END_FOR_L68
sharpenS_FOR_L7:
     movq   $0, %r15
     movq   %r15, %r12
sharpenS_FOR_COND_L10:
     movq   rows(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenS_FOR_L14
     jmp    sharpenS_END_FOR_L63
sharpenS_FOR_L14:
     movq   $2193, %rbx
     movq   %rbx, %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -96(%rbp)
     movq   $1, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   -96(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -112(%rbp)
     movq   $731, %r10
     movq   %r10, -120(%rbp)
     movq   -120(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   %r13, %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   -48(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -152(%rbp)
     movq   -112(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -160(%rbp)
     movq   -152(%rbp), %r10
     movq   -160(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   -56(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -168(%rbp)
     movq   $2193, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -184(%rbp)
     movq   $3, %r10
     movq   %r10, -192(%rbp)
     movq   -192(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -200(%rbp)
     movq   -200(%rbp), %r10
     movq   -184(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $1, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   -168(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -232(%rbp)
     movq   -232(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -240(%rbp)
     movq   -240(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $1, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -264(%rbp)
     movq   -264(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -272(%rbp)
     movq   -56(%rbp), %r10
     movq   -272(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, %r10
     movq   %r10, -280(%rbp)
     movq   -280(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenS_IF_L47
     jmp    sharpenS_END_IF_L58
sharpenS_IF_L47:
     movq   $1, %r10
     movq   %r10, -288(%rbp)
     movq   -288(%rbp), %r10
     movq   -56(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -296(%rbp)
     movq   $2193, %r10
     movq   %r10, -304(%rbp)
     movq   -304(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -312(%rbp)
     movq   -312(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -320(%rbp)
     movq   -320(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $1, %r10
     movq   %r10, -328(%rbp)
     movq   -328(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   -336(%rbp), %r10
     movq   -296(%rbp), %r11
     movq   %r11, image(, %r10, 8)
sharpenS_END_IF_L58:
     movq   $1, %r10
     movq   %r10, -352(%rbp)
     movq   -352(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r12
     jmp    sharpenS_FOR_COND_L10
sharpenS_END_FOR_L63:
     movq   $1, %r10
     movq   %r10, -360(%rbp)
     movq   -360(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -368(%rbp)
     movq   -368(%rbp), %r13
     jmp    sharpenS_FOR_COND_L3
sharpenS_END_FOR_L68:
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r15
     movq   -32(%rbp), %rbx
     movq   -40(%rbp), %r12
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
sharpenH:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*41), %rsp
     movq   %r15, -16(%rbp)
     movq   %r12, -24(%rbp)
     movq   %r13, -32(%rbp)
     movq   %rbx, -40(%rbp)
     movq   %rdi, -48(%rbp)
     movq   %rsi, -56(%rbp)
     movq   $0, %r14
     movq   %r14, %r13
sharpenH_FOR_COND_L3:
     movq   cols(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -64(%rbp)
     movq   -64(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenH_FOR_L7
     jmp    sharpenH_END_FOR_L60
sharpenH_FOR_L7:
     movq   $0, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r12
sharpenH_FOR_COND_L10:
     movq   rows(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenH_FOR_L14
     jmp    sharpenH_END_FOR_L55
sharpenH_FOR_L14:
     movq   $2193, %rbx
     movq   %rbx, %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -96(%rbp)
     movq   $731, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -112(%rbp)
     movq   %r13, %r10
     movq   -112(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -120(%rbp)
     movq   -120(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   -48(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -144(%rbp)
     movq   -96(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -152(%rbp)
     movq   -144(%rbp), %r10
     movq   -152(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -56(%rbp), %r10
     movq   %r15, %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -160(%rbp)
     movq   $2193, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -192(%rbp)
     movq   -192(%rbp), %r10
     movq   -160(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -232(%rbp)
     movq   -232(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -240(%rbp)
     movq   -56(%rbp), %r10
     movq   -240(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    sharpenH_IF_L41
     jmp    sharpenH_END_IF_L50
sharpenH_IF_L41:
     movq   $1, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   -56(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %rbx
     movq   $2193, %r10
     movq   %r10, -264(%rbp)
     movq   -264(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -272(%rbp)
     movq   -272(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -280(%rbp)
     movq   -280(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -288(%rbp)
     movq   -288(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, image(, %r10, 8)
sharpenH_END_IF_L50:
     movq   $1, %r10
     movq   %r10, -304(%rbp)
     movq   -304(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r12
     jmp    sharpenH_FOR_COND_L10
sharpenH_END_FOR_L55:
     movq   $1, %r10
     movq   %r10, -312(%rbp)
     movq   -312(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -320(%rbp)
     movq   -320(%rbp), %r13
     jmp    sharpenH_FOR_COND_L3
sharpenH_END_FOR_L60:
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %r15
     movq   -24(%rbp), %r12
     movq   -32(%rbp), %r13
     movq   -40(%rbp), %rbx
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
createUnsharpMaskV:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*192), %rsp
     movq   %r13, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %rbx, -32(%rbp)
     movq   %r15, -40(%rbp)
     movq   $3, %r15
     movq   %r15, %r10
     movq   %r10, -48(%rbp)
     movq   $0, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskV_FOR_COND_L5:
     movq   rows(%rip), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L9
     jmp    createUnsharpMaskV_END_FOR_L162
createUnsharpMaskV_FOR_L9:
     movq   $0, %r12
     movq   %r12, %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskV_FOR_COND_L12:
     movq   -48(%rbp), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L16
     jmp    createUnsharpMaskV_END_FOR_L32
createUnsharpMaskV_FOR_L16:
     movq   $2193, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   $3, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   -64(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -96(%rbp)
     movq   $731, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   -96(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -112(%rbp)
     movq   %rbx, %r10
     movq   -112(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L12
createUnsharpMaskV_END_FOR_L32:
     movq   -48(%rbp), %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskV_FOR_COND_L34:
     movq   -48(%rbp), %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L39
     jmp    createUnsharpMaskV_END_FOR_L134
createUnsharpMaskV_FOR_L39:
     movq   $0, %rbx
     movq   $731, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -152(%rbp)
     movq   -152(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   $3, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $7, %r10
     movq   %r10, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   %r14, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -192(%rbp)
     movq   $0, %r10
     movq   %r10, -200(%rbp)
     movq   -192(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -208(%rbp)
     movq   -200(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   -208(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -240(%rbp)
     movq   -240(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $4, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -264(%rbp)
     movq   $1, %r10
     movq   %r10, -272(%rbp)
     movq   -264(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -280(%rbp)
     movq   -272(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -288(%rbp)
     movq   -288(%rbp), %r10
     movq   -280(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -296(%rbp)
     movq   -296(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -304(%rbp)
     movq   -304(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -312(%rbp)
     movq   -312(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -320(%rbp)
     movq   -64(%rbp), %r10
     movq   -320(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $1, %r10
     movq   %r10, -328(%rbp)
     movq   -328(%rbp), %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   $2, %r10
     movq   %r10, -344(%rbp)
     movq   -336(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -352(%rbp)
     movq   -344(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -360(%rbp)
     movq   -360(%rbp), %r10
     movq   -352(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -368(%rbp)
     movq   -368(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -376(%rbp)
     movq   -376(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -384(%rbp)
     movq   -384(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -392(%rbp)
     movq   -392(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $2, %r10
     movq   %r10, -400(%rbp)
     movq   -400(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -408(%rbp)
     movq   $3, %r10
     movq   %r10, -416(%rbp)
     movq   -408(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -424(%rbp)
     movq   -416(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -432(%rbp)
     movq   -432(%rbp), %r10
     movq   -424(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -440(%rbp)
     movq   -440(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -448(%rbp)
     movq   -448(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -456(%rbp)
     movq   -456(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   $3, %r14
     movq   -64(%rbp), %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -464(%rbp)
     movq   -464(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $5, %r10
     movq   %r10, -472(%rbp)
     movq   -472(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -480(%rbp)
     movq   $4, %r10
     movq   %r10, -488(%rbp)
     movq   -480(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -496(%rbp)
     movq   -488(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -504(%rbp)
     movq   -504(%rbp), %r10
     movq   -496(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -512(%rbp)
     movq   -512(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -520(%rbp)
     movq   -520(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -528(%rbp)
     movq   -528(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -536(%rbp)
     movq   -536(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $8, %r10
     movq   %r10, -544(%rbp)
     movq   -544(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -552(%rbp)
     movq   $5, %r10
     movq   %r10, -560(%rbp)
     movq   -552(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -568(%rbp)
     movq   -560(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -576(%rbp)
     movq   -576(%rbp), %r10
     movq   -568(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -584(%rbp)
     movq   -584(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -592(%rbp)
     movq   -592(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -600(%rbp)
     movq   -600(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -608(%rbp)
     movq   -64(%rbp), %r10
     movq   -608(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -616(%rbp)
     movq   -616(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $11, %r10
     movq   %r10, -624(%rbp)
     movq   -624(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -632(%rbp)
     movq   $6, %r10
     movq   %r10, -640(%rbp)
     movq   -632(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -648(%rbp)
     movq   -640(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -656(%rbp)
     movq   -656(%rbp), %r10
     movq   -648(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -664(%rbp)
     movq   -664(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r10, -232(%rbp)
     movq   $731, %r10
     movq   %r10, -672(%rbp)
     movq   -672(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -680(%rbp)
     movq   -680(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -688(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -688(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %rbx
     movq   $731, %r10
     movq   %r10, -696(%rbp)
     movq   -696(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   -64(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -704(%rbp)
     movq   -704(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -720(%rbp)
     movq   -720(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L34
createUnsharpMaskV_END_FOR_L134:
     movq   -48(%rbp), %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -728(%rbp)
     movq   -728(%rbp), %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskV_FOR_COND_L137:
     movq   cols(%rip), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -736(%rbp)
     movq   -736(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L141
     jmp    createUnsharpMaskV_END_FOR_L157
createUnsharpMaskV_FOR_L141:
     movq   $2193, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -744(%rbp)
     movq   -744(%rbp), %r10
     movq   -64(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $2, %r10
     movq   %r10, -752(%rbp)
     movq   -752(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -760(%rbp)
     movq   $731, %r10
     movq   %r10, -768(%rbp)
     movq   -768(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -776(%rbp)
     movq   -760(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -784(%rbp)
     movq   -776(%rbp), %r10
     movq   -784(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -800(%rbp)
     movq   -800(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L137
createUnsharpMaskV_END_FOR_L157:
     movq   $1, %rbx
     movq   %rbx, %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L5
createUnsharpMaskV_END_FOR_L162:
     movq   -48(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -808(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -808(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -816(%rbp)
     movq   -48(%rbp), %r10
     movq   -816(%rbp), %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $0, %r13
     movq   %r13, %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskV_FOR_COND_L167:
     movq   cols(%rip), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -832(%rbp)
     movq   -832(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L171
     jmp    createUnsharpMaskV_END_FOR_L316
createUnsharpMaskV_FOR_L171:
     movq   -64(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -840(%rbp)
     movq   -840(%rbp), %r10
     movq   %r10, -848(%rbp)
     movq   $731, %r14
     movq   %r14, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -856(%rbp)
     movq   -856(%rbp), %r10
     movq   %r10, -864(%rbp)
     movq   $1462, %rbx
     movq   %rbx, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -872(%rbp)
     movq   -872(%rbp), %r10
     movq   %r10, -880(%rbp)
     movq   $0, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskV_FOR_COND_L181:
     movq   -48(%rbp), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -888(%rbp)
     movq   -888(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L185
     jmp    createUnsharpMaskV_END_FOR_L195
createUnsharpMaskV_FOR_L185:
     movq   $0, %r14
     movq   $731, %r10
     movq   %r10, -896(%rbp)
     movq   -896(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r14, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L181
createUnsharpMaskV_END_FOR_L195:
     movq   -48(%rbp), %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskV_FOR_COND_L197:
     movq   -48(%rbp), %r10
     movq   rows(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -912(%rbp)
     movq   -912(%rbp), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -920(%rbp)
     movq   -920(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L202
     jmp    createUnsharpMaskV_END_FOR_L294
createUnsharpMaskV_FOR_L202:
     movq   $0, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r13
     movq   %r13, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -936(%rbp)
     movq   -64(%rbp), %r10
     movq   -936(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $0, %r15
     movq   %r15, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -944(%rbp)
     movq   -944(%rbp), %r10
     movq   -848(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -952(%rbp)
     movq   %rbx, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -960(%rbp)
     movq   -952(%rbp), %r10
     movq   -960(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -968(%rbp)
     movq   -968(%rbp), %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -976(%rbp)
     movq   -976(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -984(%rbp)
     movq   -64(%rbp), %r10
     movq   -984(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -992(%rbp)
     movq   $1, %r10
     movq   %r10, -1000(%rbp)
     movq   -1000(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1008(%rbp)
     movq   -1008(%rbp), %r10
     movq   -864(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1016(%rbp)
     movq   -992(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1024(%rbp)
     movq   -1016(%rbp), %r10
     movq   -1024(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1032(%rbp)
     movq   -1032(%rbp), %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -1040(%rbp)
     movq   -1040(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1048(%rbp)
     movq   -64(%rbp), %r10
     movq   -1048(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1056(%rbp)
     movq   $2, %r10
     movq   %r10, -1064(%rbp)
     movq   -1064(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1072(%rbp)
     movq   -1072(%rbp), %r10
     movq   -880(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -1056(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1080(%rbp)
     movq   %r15, %r10
     movq   -1080(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %rbx
     movq   %rbx, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1088(%rbp)
     movq   -64(%rbp), %r10
     movq   -1088(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1096(%rbp)
     movq   $731, %r10
     movq   %r10, -1104(%rbp)
     movq   -56(%rbp), %r10
     movq   -1104(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1112(%rbp)
     movq   -64(%rbp), %r10
     movq   -1112(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1120(%rbp)
     movq   $3, %r10
     movq   %r10, -1128(%rbp)
     movq   -1120(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1136(%rbp)
     movq   -1128(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1144(%rbp)
     movq   -1144(%rbp), %r10
     movq   -1136(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1152(%rbp)
     movq   -1096(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1160(%rbp)
     movq   -1152(%rbp), %r10
     movq   -1160(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -1168(%rbp)
     movq   -1168(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1176(%rbp)
     movq   -64(%rbp), %r10
     movq   -1176(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1184(%rbp)
     movq   $731, %r10
     movq   %r10, -1192(%rbp)
     movq   -56(%rbp), %r10
     movq   -1192(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1200(%rbp)
     movq   -64(%rbp), %r10
     movq   -1200(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1208(%rbp)
     movq   $731, %rbx
     movq   %rbx, %r10
     movq   -1208(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1216(%rbp)
     movq   $4, %r10
     movq   %r10, -1224(%rbp)
     movq   -1216(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1232(%rbp)
     movq   -1224(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1240(%rbp)
     movq   -1240(%rbp), %r10
     movq   -1232(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   -1184(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1248(%rbp)
     movq   %r14, %r10
     movq   -1248(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1256(%rbp)
     movq   -64(%rbp), %r10
     movq   -1256(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1264(%rbp)
     movq   $731, %r10
     movq   %r10, -1272(%rbp)
     movq   -56(%rbp), %r10
     movq   -1272(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1280(%rbp)
     movq   -64(%rbp), %r10
     movq   -1280(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1288(%rbp)
     movq   $1462, %r10
     movq   %r10, -1296(%rbp)
     movq   -1296(%rbp), %r10
     movq   -1288(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1304(%rbp)
     movq   $5, %r14
     movq   -1304(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1312(%rbp)
     movq   %r14, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1320(%rbp)
     movq   -1320(%rbp), %r10
     movq   -1312(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   -1264(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1328(%rbp)
     movq   %r13, %r10
     movq   -1328(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -1336(%rbp)
     movq   -1336(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1344(%rbp)
     movq   -64(%rbp), %r10
     movq   -1344(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1352(%rbp)
     movq   $731, %r10
     movq   %r10, -1360(%rbp)
     movq   -56(%rbp), %r10
     movq   -1360(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1368(%rbp)
     movq   -64(%rbp), %r10
     movq   -1368(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1376(%rbp)
     movq   $2193, %r10
     movq   %r10, -1384(%rbp)
     movq   -1384(%rbp), %r10
     movq   -1376(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1392(%rbp)
     movq   $6, %r14
     movq   -1392(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1400(%rbp)
     movq   %r14, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1408(%rbp)
     movq   -1408(%rbp), %r10
     movq   -1400(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   -1352(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1416(%rbp)
     movq   %rbx, %r10
     movq   -1416(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -928(%rbp)
     movq   -864(%rbp), %r10
     movq   %r10, -848(%rbp)
     movq   -880(%rbp), %r10
     movq   %r10, -864(%rbp)
     movq   $731, %r10
     movq   %r10, -1424(%rbp)
     movq   -1424(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1432(%rbp)
     movq   -64(%rbp), %r10
     movq   -1432(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1440(%rbp)
     movq   -1440(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1448(%rbp)
     movq   -1448(%rbp), %r10
     movq   %r10, -880(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -928(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r12
     movq   $731, %r14
     movq   %r14, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1456(%rbp)
     movq   -64(%rbp), %r10
     movq   -1456(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r12, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -1472(%rbp)
     movq   -1472(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L197
createUnsharpMaskV_END_FOR_L294:
     movq   -48(%rbp), %r10
     movq   rows(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1480(%rbp)
     movq   -1480(%rbp), %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskV_FOR_COND_L297:
     movq   rows(%rip), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskV_FOR_L301
     jmp    createUnsharpMaskV_END_FOR_L311
createUnsharpMaskV_FOR_L301:
     movq   $0, %r10
     movq   %r10, -1488(%rbp)
     movq   $731, %r12
     movq   %r12, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   -1488(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r13
     movq   %r13, %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1504(%rbp)
     movq   -1504(%rbp), %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L297
createUnsharpMaskV_END_FOR_L311:
     movq   $1, %r10
     movq   %r10, -1512(%rbp)
     movq   -1512(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskV_FOR_COND_L167
createUnsharpMaskV_END_FOR_L316:
     movq   -48(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1520(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -1520(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   -48(%rbp), %r10
     movq   %r12, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %rbx
     movq   -40(%rbp), %r15
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
createUnsharpMaskS:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*192), %rsp
     movq   %r13, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %rbx, -32(%rbp)
     movq   %r15, -40(%rbp)
     movq   $3, %r15
     movq   %r15, %r10
     movq   %r10, -48(%rbp)
     movq   $0, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskS_FOR_COND_L5:
     movq   rows(%rip), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L9
     jmp    createUnsharpMaskS_END_FOR_L162
createUnsharpMaskS_FOR_L9:
     movq   $0, %r12
     movq   %r12, %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskS_FOR_COND_L12:
     movq   -48(%rbp), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L16
     jmp    createUnsharpMaskS_END_FOR_L32
createUnsharpMaskS_FOR_L16:
     movq   $2193, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   $3, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   -64(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $1, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -96(%rbp)
     movq   $731, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   -96(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -112(%rbp)
     movq   %rbx, %r10
     movq   -112(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L12
createUnsharpMaskS_END_FOR_L32:
     movq   -48(%rbp), %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskS_FOR_COND_L34:
     movq   -48(%rbp), %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L39
     jmp    createUnsharpMaskS_END_FOR_L134
createUnsharpMaskS_FOR_L39:
     movq   $0, %rbx
     movq   $731, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -152(%rbp)
     movq   -152(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   $3, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $8, %r10
     movq   %r10, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   %r14, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -192(%rbp)
     movq   $0, %r10
     movq   %r10, -200(%rbp)
     movq   -192(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -208(%rbp)
     movq   -200(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   -208(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -240(%rbp)
     movq   -240(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $5, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -264(%rbp)
     movq   $1, %r10
     movq   %r10, -272(%rbp)
     movq   -264(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -280(%rbp)
     movq   -272(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -288(%rbp)
     movq   -288(%rbp), %r10
     movq   -280(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -296(%rbp)
     movq   -296(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -304(%rbp)
     movq   -304(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -312(%rbp)
     movq   -312(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -320(%rbp)
     movq   -64(%rbp), %r10
     movq   -320(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $2, %r10
     movq   %r10, -328(%rbp)
     movq   -328(%rbp), %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   $2, %r10
     movq   %r10, -344(%rbp)
     movq   -336(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -352(%rbp)
     movq   -344(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -360(%rbp)
     movq   -360(%rbp), %r10
     movq   -352(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -368(%rbp)
     movq   -368(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -376(%rbp)
     movq   -376(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -384(%rbp)
     movq   -384(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -392(%rbp)
     movq   -392(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $1, %r10
     movq   %r10, -400(%rbp)
     movq   -400(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -408(%rbp)
     movq   $3, %r10
     movq   %r10, -416(%rbp)
     movq   -408(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -424(%rbp)
     movq   -416(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -432(%rbp)
     movq   -432(%rbp), %r10
     movq   -424(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -440(%rbp)
     movq   -440(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -448(%rbp)
     movq   -448(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -456(%rbp)
     movq   -456(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   $3, %r14
     movq   -64(%rbp), %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -464(%rbp)
     movq   -464(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $4, %r10
     movq   %r10, -472(%rbp)
     movq   -472(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -480(%rbp)
     movq   $4, %r10
     movq   %r10, -488(%rbp)
     movq   -480(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -496(%rbp)
     movq   -488(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -504(%rbp)
     movq   -504(%rbp), %r10
     movq   -496(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -512(%rbp)
     movq   -512(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -520(%rbp)
     movq   -520(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -528(%rbp)
     movq   -528(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -536(%rbp)
     movq   -536(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $7, %r10
     movq   %r10, -544(%rbp)
     movq   -544(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -552(%rbp)
     movq   $5, %r10
     movq   %r10, -560(%rbp)
     movq   -552(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -568(%rbp)
     movq   -560(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -576(%rbp)
     movq   -576(%rbp), %r10
     movq   -568(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -584(%rbp)
     movq   -584(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -592(%rbp)
     movq   -592(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -600(%rbp)
     movq   -600(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -608(%rbp)
     movq   -64(%rbp), %r10
     movq   -608(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -616(%rbp)
     movq   -616(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $10, %r10
     movq   %r10, -624(%rbp)
     movq   -624(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -632(%rbp)
     movq   $6, %r10
     movq   %r10, -640(%rbp)
     movq   -632(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -648(%rbp)
     movq   -640(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -656(%rbp)
     movq   -656(%rbp), %r10
     movq   -648(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -664(%rbp)
     movq   -664(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r10, -232(%rbp)
     movq   $731, %r10
     movq   %r10, -672(%rbp)
     movq   -672(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -680(%rbp)
     movq   -680(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -688(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -688(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %rbx
     movq   $731, %r10
     movq   %r10, -696(%rbp)
     movq   -696(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   -64(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -704(%rbp)
     movq   -704(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -720(%rbp)
     movq   -720(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L34
createUnsharpMaskS_END_FOR_L134:
     movq   -48(%rbp), %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -728(%rbp)
     movq   -728(%rbp), %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskS_FOR_COND_L137:
     movq   cols(%rip), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -736(%rbp)
     movq   -736(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L141
     jmp    createUnsharpMaskS_END_FOR_L157
createUnsharpMaskS_FOR_L141:
     movq   $2193, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -744(%rbp)
     movq   -744(%rbp), %r10
     movq   -64(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $1, %r10
     movq   %r10, -752(%rbp)
     movq   -752(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -760(%rbp)
     movq   $731, %r10
     movq   %r10, -768(%rbp)
     movq   -768(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -776(%rbp)
     movq   -760(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -784(%rbp)
     movq   -776(%rbp), %r10
     movq   -784(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -800(%rbp)
     movq   -800(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L137
createUnsharpMaskS_END_FOR_L157:
     movq   $1, %rbx
     movq   %rbx, %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L5
createUnsharpMaskS_END_FOR_L162:
     movq   -48(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -808(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -808(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -816(%rbp)
     movq   -48(%rbp), %r10
     movq   -816(%rbp), %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $0, %r13
     movq   %r13, %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskS_FOR_COND_L167:
     movq   cols(%rip), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -832(%rbp)
     movq   -832(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L171
     jmp    createUnsharpMaskS_END_FOR_L316
createUnsharpMaskS_FOR_L171:
     movq   -64(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -840(%rbp)
     movq   -840(%rbp), %r10
     movq   %r10, -848(%rbp)
     movq   $731, %r14
     movq   %r14, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -856(%rbp)
     movq   -856(%rbp), %r10
     movq   %r10, -864(%rbp)
     movq   $1462, %rbx
     movq   %rbx, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -872(%rbp)
     movq   -872(%rbp), %r10
     movq   %r10, -880(%rbp)
     movq   $0, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskS_FOR_COND_L181:
     movq   -48(%rbp), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -888(%rbp)
     movq   -888(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L185
     jmp    createUnsharpMaskS_END_FOR_L195
createUnsharpMaskS_FOR_L185:
     movq   $0, %r14
     movq   $731, %r10
     movq   %r10, -896(%rbp)
     movq   -896(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r14, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L181
createUnsharpMaskS_END_FOR_L195:
     movq   -48(%rbp), %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskS_FOR_COND_L197:
     movq   -48(%rbp), %r10
     movq   rows(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -912(%rbp)
     movq   -912(%rbp), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -920(%rbp)
     movq   -920(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L202
     jmp    createUnsharpMaskS_END_FOR_L294
createUnsharpMaskS_FOR_L202:
     movq   $0, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r13
     movq   %r13, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -936(%rbp)
     movq   -64(%rbp), %r10
     movq   -936(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $0, %r15
     movq   %r15, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -944(%rbp)
     movq   -944(%rbp), %r10
     movq   -848(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -952(%rbp)
     movq   %rbx, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -960(%rbp)
     movq   -952(%rbp), %r10
     movq   -960(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -968(%rbp)
     movq   -968(%rbp), %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -976(%rbp)
     movq   -976(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -984(%rbp)
     movq   -64(%rbp), %r10
     movq   -984(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -992(%rbp)
     movq   $1, %r10
     movq   %r10, -1000(%rbp)
     movq   -1000(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1008(%rbp)
     movq   -1008(%rbp), %r10
     movq   -864(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1016(%rbp)
     movq   -992(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1024(%rbp)
     movq   -1016(%rbp), %r10
     movq   -1024(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1032(%rbp)
     movq   -1032(%rbp), %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -1040(%rbp)
     movq   -1040(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1048(%rbp)
     movq   -64(%rbp), %r10
     movq   -1048(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1056(%rbp)
     movq   $2, %r10
     movq   %r10, -1064(%rbp)
     movq   -1064(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1072(%rbp)
     movq   -1072(%rbp), %r10
     movq   -880(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -1056(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1080(%rbp)
     movq   %r15, %r10
     movq   -1080(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %rbx
     movq   %rbx, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1088(%rbp)
     movq   -64(%rbp), %r10
     movq   -1088(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1096(%rbp)
     movq   $731, %r10
     movq   %r10, -1104(%rbp)
     movq   -56(%rbp), %r10
     movq   -1104(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1112(%rbp)
     movq   -64(%rbp), %r10
     movq   -1112(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1120(%rbp)
     movq   $3, %r10
     movq   %r10, -1128(%rbp)
     movq   -1120(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1136(%rbp)
     movq   -1128(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1144(%rbp)
     movq   -1144(%rbp), %r10
     movq   -1136(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1152(%rbp)
     movq   -1096(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1160(%rbp)
     movq   -1152(%rbp), %r10
     movq   -1160(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -1168(%rbp)
     movq   -1168(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1176(%rbp)
     movq   -64(%rbp), %r10
     movq   -1176(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1184(%rbp)
     movq   $731, %r10
     movq   %r10, -1192(%rbp)
     movq   -56(%rbp), %r10
     movq   -1192(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1200(%rbp)
     movq   -64(%rbp), %r10
     movq   -1200(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1208(%rbp)
     movq   $731, %rbx
     movq   %rbx, %r10
     movq   -1208(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1216(%rbp)
     movq   $4, %r10
     movq   %r10, -1224(%rbp)
     movq   -1216(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1232(%rbp)
     movq   -1224(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1240(%rbp)
     movq   -1240(%rbp), %r10
     movq   -1232(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   -1184(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1248(%rbp)
     movq   %r14, %r10
     movq   -1248(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1256(%rbp)
     movq   -64(%rbp), %r10
     movq   -1256(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1264(%rbp)
     movq   $731, %r10
     movq   %r10, -1272(%rbp)
     movq   -56(%rbp), %r10
     movq   -1272(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1280(%rbp)
     movq   -64(%rbp), %r10
     movq   -1280(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1288(%rbp)
     movq   $1462, %r10
     movq   %r10, -1296(%rbp)
     movq   -1296(%rbp), %r10
     movq   -1288(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1304(%rbp)
     movq   $5, %r14
     movq   -1304(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1312(%rbp)
     movq   %r14, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1320(%rbp)
     movq   -1320(%rbp), %r10
     movq   -1312(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   -1264(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1328(%rbp)
     movq   %r13, %r10
     movq   -1328(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -928(%rbp)
     movq   $731, %r10
     movq   %r10, -1336(%rbp)
     movq   -1336(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1344(%rbp)
     movq   -64(%rbp), %r10
     movq   -1344(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1352(%rbp)
     movq   $731, %r10
     movq   %r10, -1360(%rbp)
     movq   -56(%rbp), %r10
     movq   -1360(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1368(%rbp)
     movq   -64(%rbp), %r10
     movq   -1368(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1376(%rbp)
     movq   $2193, %r10
     movq   %r10, -1384(%rbp)
     movq   -1384(%rbp), %r10
     movq   -1376(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1392(%rbp)
     movq   $6, %r14
     movq   -1392(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1400(%rbp)
     movq   %r14, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1408(%rbp)
     movq   -1408(%rbp), %r10
     movq   -1400(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   -1352(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1416(%rbp)
     movq   %rbx, %r10
     movq   -1416(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -928(%rbp)
     movq   -864(%rbp), %r10
     movq   %r10, -848(%rbp)
     movq   -880(%rbp), %r10
     movq   %r10, -864(%rbp)
     movq   $731, %r10
     movq   %r10, -1424(%rbp)
     movq   -1424(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1432(%rbp)
     movq   -64(%rbp), %r10
     movq   -1432(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1440(%rbp)
     movq   -1440(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1448(%rbp)
     movq   -1448(%rbp), %r10
     movq   %r10, -880(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -928(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r12
     movq   $731, %r14
     movq   %r14, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1456(%rbp)
     movq   -64(%rbp), %r10
     movq   -1456(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r12, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -1472(%rbp)
     movq   -1472(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L197
createUnsharpMaskS_END_FOR_L294:
     movq   -48(%rbp), %r10
     movq   rows(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1480(%rbp)
     movq   -1480(%rbp), %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskS_FOR_COND_L297:
     movq   rows(%rip), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskS_FOR_L301
     jmp    createUnsharpMaskS_END_FOR_L311
createUnsharpMaskS_FOR_L301:
     movq   $0, %r10
     movq   %r10, -1488(%rbp)
     movq   $731, %r12
     movq   %r12, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   -1488(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r13
     movq   %r13, %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1504(%rbp)
     movq   -1504(%rbp), %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L297
createUnsharpMaskS_END_FOR_L311:
     movq   $1, %r10
     movq   %r10, -1512(%rbp)
     movq   -1512(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskS_FOR_COND_L167
createUnsharpMaskS_END_FOR_L316:
     movq   -48(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1520(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -1520(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   -48(%rbp), %r10
     movq   %r12, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %rbx
     movq   -40(%rbp), %r15
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
createUnsharpMaskH:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*190), %rsp
     movq   %r13, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %r15, -32(%rbp)
     movq   %rbx, -40(%rbp)
     movq   $3, %r14
     movq   %r14, %r10
     movq   %r10, -48(%rbp)
     movq   $0, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskH_FOR_COND_L5:
     movq   rows(%rip), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L9
     jmp    createUnsharpMaskH_END_FOR_L156
createUnsharpMaskH_FOR_L9:
     movq   $0, %r12
     movq   %r12, %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskH_FOR_COND_L12:
     movq   -48(%rbp), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L16
     jmp    createUnsharpMaskH_END_FOR_L30
createUnsharpMaskH_FOR_L16:
     movq   $2193, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   $3, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r10
     movq   -64(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -96(%rbp)
     movq   $731, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   -96(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -112(%rbp)
     movq   %rbx, %r10
     movq   -112(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r15
     movq   %r15, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L12
createUnsharpMaskH_END_FOR_L30:
     movq   -48(%rbp), %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskH_FOR_COND_L32:
     movq   -48(%rbp), %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L37
     jmp    createUnsharpMaskH_END_FOR_L130
createUnsharpMaskH_FOR_L37:
     movq   $0, %rbx
     movq   $731, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -152(%rbp)
     movq   -152(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   $3, %r14
     movq   -64(%rbp), %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $9, %r10
     movq   %r10, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -192(%rbp)
     movq   $0, %r10
     movq   %r10, -200(%rbp)
     movq   -192(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -208(%rbp)
     movq   -200(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   -208(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -240(%rbp)
     movq   -240(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $6, %r10
     movq   %r10, -264(%rbp)
     movq   -264(%rbp), %r10
     movq   %r13, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -272(%rbp)
     movq   $1, %r10
     movq   %r10, -280(%rbp)
     movq   -272(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -288(%rbp)
     movq   -280(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -296(%rbp)
     movq   -296(%rbp), %r10
     movq   -288(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -304(%rbp)
     movq   -304(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -312(%rbp)
     movq   -312(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -320(%rbp)
     movq   -320(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -328(%rbp)
     movq   -64(%rbp), %r10
     movq   -328(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   -336(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $3, %r10
     movq   %r10, -344(%rbp)
     movq   -344(%rbp), %r10
     movq   %r12, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -352(%rbp)
     movq   $2, %r10
     movq   %r10, -360(%rbp)
     movq   -352(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -368(%rbp)
     movq   -360(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -376(%rbp)
     movq   -376(%rbp), %r10
     movq   -368(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -384(%rbp)
     movq   -384(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -392(%rbp)
     movq   -392(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -400(%rbp)
     movq   -400(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -408(%rbp)
     movq   $3, %r10
     movq   %r10, -416(%rbp)
     movq   -408(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -424(%rbp)
     movq   -416(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -432(%rbp)
     movq   -432(%rbp), %r10
     movq   -424(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -440(%rbp)
     movq   -440(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -448(%rbp)
     movq   -448(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -456(%rbp)
     movq   -456(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   $3, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -464(%rbp)
     movq   -464(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -472(%rbp)
     movq   -472(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -480(%rbp)
     movq   $4, %r10
     movq   %r10, -488(%rbp)
     movq   -480(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -496(%rbp)
     movq   -488(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -504(%rbp)
     movq   -504(%rbp), %r10
     movq   -496(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -512(%rbp)
     movq   -512(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -520(%rbp)
     movq   -520(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -528(%rbp)
     movq   -528(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   $6, %r10
     movq   %r10, -536(%rbp)
     movq   -536(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -544(%rbp)
     movq   $5, %r10
     movq   %r10, -552(%rbp)
     movq   -544(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -560(%rbp)
     movq   -552(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -568(%rbp)
     movq   -568(%rbp), %r10
     movq   -560(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -576(%rbp)
     movq   -576(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -584(%rbp)
     movq   -584(%rbp), %r10
     movq   %r10, -232(%rbp)
     movq   $2193, %r10
     movq   %r10, -592(%rbp)
     movq   -592(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -600(%rbp)
     movq   -64(%rbp), %r10
     movq   -600(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -608(%rbp)
     movq   -608(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $9, %r10
     movq   %r10, -616(%rbp)
     movq   -616(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -624(%rbp)
     movq   $6, %r10
     movq   %r10, -632(%rbp)
     movq   -624(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -640(%rbp)
     movq   -632(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -648(%rbp)
     movq   -648(%rbp), %r10
     movq   -640(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -656(%rbp)
     movq   -656(%rbp), %r10
     movq   -232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r10, -232(%rbp)
     movq   $731, %r10
     movq   %r10, -664(%rbp)
     movq   -664(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -672(%rbp)
     movq   -672(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -680(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -680(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -688(%rbp)
     movq   $731, %r14
     movq   %r14, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -696(%rbp)
     movq   -696(%rbp), %r10
     movq   -688(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -712(%rbp)
     movq   -712(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L32
createUnsharpMaskH_END_FOR_L130:
     movq   -48(%rbp), %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskH_FOR_COND_L133:
     movq   cols(%rip), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L137
     jmp    createUnsharpMaskH_END_FOR_L151
createUnsharpMaskH_FOR_L137:
     movq   $2193, %r10
     movq   %r10, -720(%rbp)
     movq   -720(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   $3, %r10
     movq   %r10, -728(%rbp)
     movq   -728(%rbp), %r10
     movq   -64(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -736(%rbp)
     movq   -736(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -744(%rbp)
     movq   $731, %r10
     movq   %r10, -752(%rbp)
     movq   -752(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r13
     movq   -64(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   -744(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -760(%rbp)
     movq   %rbx, %r10
     movq   -760(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r15
     movq   %r15, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L133
createUnsharpMaskH_END_FOR_L151:
     movq   $1, %r10
     movq   %r10, -776(%rbp)
     movq   -776(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -784(%rbp)
     movq   -784(%rbp), %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L5
createUnsharpMaskH_END_FOR_L156:
     movq   -48(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -792(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -792(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -800(%rbp)
     movq   -48(%rbp), %r10
     movq   -800(%rbp), %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $0, %r10
     movq   %r10, -816(%rbp)
     movq   -816(%rbp), %r10
     movq   %r10, -64(%rbp)
createUnsharpMaskH_FOR_COND_L161:
     movq   cols(%rip), %r10
     movq   -64(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L165
     jmp    createUnsharpMaskH_END_FOR_L310
createUnsharpMaskH_FOR_L165:
     movq   -64(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -824(%rbp)
     movq   -824(%rbp), %r10
     movq   %r10, -832(%rbp)
     movq   $731, %r12
     movq   %r12, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -840(%rbp)
     movq   -840(%rbp), %r10
     movq   %r10, -848(%rbp)
     movq   $1462, %rbx
     movq   %rbx, %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -856(%rbp)
     movq   -856(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -864(%rbp)
     movq   -864(%rbp), %r10
     movq   %r10, -872(%rbp)
     movq   $0, %r14
     movq   %r14, %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskH_FOR_COND_L175:
     movq   -48(%rbp), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -880(%rbp)
     movq   -880(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L179
     jmp    createUnsharpMaskH_END_FOR_L189
createUnsharpMaskH_FOR_L179:
     movq   $0, %r13
     movq   $731, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r12
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r13, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -896(%rbp)
     movq   -896(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L175
createUnsharpMaskH_END_FOR_L189:
     movq   -48(%rbp), %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskH_FOR_COND_L191:
     movq   -48(%rbp), %r10
     movq   rows(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -904(%rbp)
     movq   -904(%rbp), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -912(%rbp)
     movq   -912(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L196
     jmp    createUnsharpMaskH_END_FOR_L288
createUnsharpMaskH_FOR_L196:
     movq   $0, %r12
     movq   %r12, %r10
     movq   %r10, -920(%rbp)
     movq   $731, %r14
     movq   %r14, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -928(%rbp)
     movq   -64(%rbp), %r10
     movq   -928(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $0, %r15
     movq   %r15, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -936(%rbp)
     movq   -936(%rbp), %r10
     movq   -832(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -944(%rbp)
     movq   %rbx, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -952(%rbp)
     movq   -944(%rbp), %r10
     movq   -952(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -960(%rbp)
     movq   -960(%rbp), %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -920(%rbp)
     movq   $731, %r10
     movq   %r10, -968(%rbp)
     movq   -968(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -976(%rbp)
     movq   -64(%rbp), %r10
     movq   -976(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -984(%rbp)
     movq   $1, %r10
     movq   %r10, -992(%rbp)
     movq   -992(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1000(%rbp)
     movq   -1000(%rbp), %r10
     movq   -848(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1008(%rbp)
     movq   -984(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1016(%rbp)
     movq   -1008(%rbp), %r10
     movq   -1016(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1024(%rbp)
     movq   -1024(%rbp), %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   %r10, -920(%rbp)
     movq   $731, %r15
     movq   %r15, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1032(%rbp)
     movq   -64(%rbp), %r10
     movq   -1032(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   $2, %r10
     movq   %r10, -1040(%rbp)
     movq   -1040(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1048(%rbp)
     movq   -1048(%rbp), %r10
     movq   -872(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   %r13, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1056(%rbp)
     movq   %rbx, %r10
     movq   -1056(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1064(%rbp)
     movq   -1064(%rbp), %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -920(%rbp)
     movq   $731, %r10
     movq   %r10, -1072(%rbp)
     movq   -1072(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1080(%rbp)
     movq   -64(%rbp), %r10
     movq   -1080(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1088(%rbp)
     movq   $731, %r10
     movq   %r10, -1096(%rbp)
     movq   -56(%rbp), %r10
     movq   -1096(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1104(%rbp)
     movq   -64(%rbp), %r10
     movq   -1104(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $3, %r14
     movq   %r15, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1112(%rbp)
     movq   %r14, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1120(%rbp)
     movq   -1120(%rbp), %r10
     movq   -1112(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1128(%rbp)
     movq   -1088(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1136(%rbp)
     movq   -1128(%rbp), %r10
     movq   -1136(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -920(%rbp)
     movq   $731, %r10
     movq   %r10, -1144(%rbp)
     movq   -1144(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1152(%rbp)
     movq   -64(%rbp), %r10
     movq   -1152(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1160(%rbp)
     movq   $731, %r10
     movq   %r10, -1168(%rbp)
     movq   -56(%rbp), %r10
     movq   -1168(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1176(%rbp)
     movq   -64(%rbp), %r10
     movq   -1176(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1184(%rbp)
     movq   $731, %r10
     movq   %r10, -1192(%rbp)
     movq   -1192(%rbp), %r10
     movq   -1184(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1200(%rbp)
     movq   $4, %r10
     movq   %r10, -1208(%rbp)
     movq   -1200(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1216(%rbp)
     movq   -1208(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1224(%rbp)
     movq   -1224(%rbp), %r10
     movq   -1216(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -1160(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1232(%rbp)
     movq   %r15, %r10
     movq   -1232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -920(%rbp)
     movq   $731, %rbx
     movq   %rbx, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1240(%rbp)
     movq   -64(%rbp), %r10
     movq   -1240(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1248(%rbp)
     movq   $731, %r10
     movq   %r10, -1256(%rbp)
     movq   -56(%rbp), %r10
     movq   -1256(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1264(%rbp)
     movq   -64(%rbp), %r10
     movq   -1264(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1272(%rbp)
     movq   $1462, %r13
     movq   %r13, %r10
     movq   -1272(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1280(%rbp)
     movq   $5, %r10
     movq   %r10, -1288(%rbp)
     movq   -1280(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1296(%rbp)
     movq   -1288(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1304(%rbp)
     movq   -1304(%rbp), %r10
     movq   -1296(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -1248(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1312(%rbp)
     movq   %r15, %r10
     movq   -1312(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1320(%rbp)
     movq   -1320(%rbp), %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   %r12, %r10
     movq   %r10, -920(%rbp)
     movq   $731, %rbx
     movq   %rbx, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1328(%rbp)
     movq   -64(%rbp), %r10
     movq   -1328(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1336(%rbp)
     movq   $731, %r10
     movq   %r10, -1344(%rbp)
     movq   -56(%rbp), %r10
     movq   -1344(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1352(%rbp)
     movq   -64(%rbp), %r10
     movq   -1352(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1360(%rbp)
     movq   $2193, %r14
     movq   %r14, %r10
     movq   -1360(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1368(%rbp)
     movq   $6, %r15
     movq   -1368(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1376(%rbp)
     movq   %r15, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1384(%rbp)
     movq   -1384(%rbp), %r10
     movq   -1376(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1392(%rbp)
     movq   -1336(%rbp), %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1400(%rbp)
     movq   -1392(%rbp), %r10
     movq   -1400(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1408(%rbp)
     movq   -1408(%rbp), %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -920(%rbp)
     movq   -848(%rbp), %r10
     movq   %r10, -832(%rbp)
     movq   -872(%rbp), %r10
     movq   %r10, -848(%rbp)
     movq   $731, %r10
     movq   %r10, -1416(%rbp)
     movq   -1416(%rbp), %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1424(%rbp)
     movq   -64(%rbp), %r10
     movq   -1424(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %rax
     movq   unsharpMask( ,%rax, 8), %rax
     movq   %rax, -1432(%rbp)
     movq   -1432(%rbp), %r10
     movq   %r10, -872(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -920(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r12
     movq   $731, %r14
     movq   %r14, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1440(%rbp)
     movq   -64(%rbp), %r10
     movq   -1440(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %r12, %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -1456(%rbp)
     movq   -1456(%rbp), %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r13
     movq   %r13, %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L191
createUnsharpMaskH_END_FOR_L288:
     movq   -48(%rbp), %r10
     movq   rows(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1464(%rbp)
     movq   -1464(%rbp), %r10
     movq   %r10, -56(%rbp)
createUnsharpMaskH_FOR_COND_L291:
     movq   rows(%rip), %r10
     movq   -56(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createUnsharpMaskH_FOR_L295
     jmp    createUnsharpMaskH_END_FOR_L305
createUnsharpMaskH_FOR_L295:
     movq   $0, %r10
     movq   %r10, -1472(%rbp)
     movq   $731, %r12
     movq   %r12, %r10
     movq   -56(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   -64(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   -1472(%rbp), %r11
     movq   %r11, unsharpMask(, %r10, 8)
     movq   $1, %r13
     movq   %r13, %r10
     movq   -56(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1488(%rbp)
     movq   -1488(%rbp), %r10
     movq   %r10, -56(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L291
createUnsharpMaskH_END_FOR_L305:
     movq   $1, %r10
     movq   %r10, -1496(%rbp)
     movq   -1496(%rbp), %r10
     movq   -64(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   %r10, -64(%rbp)
     jmp    createUnsharpMaskH_FOR_COND_L161
createUnsharpMaskH_END_FOR_L310:
     movq   -48(%rbp), %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -1504(%rbp)
     movq   kernel_sum(%rip), %r10
     movq   -1504(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r12
     movq   -48(%rbp), %r10
     movq   %r12, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %r15
     movq   -40(%rbp), %rbx
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
createKernel:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*28), %rsp
     movq   %r15, -16(%rbp)
     movq   %rbx, -24(%rbp)
     movq   %r12, -32(%rbp)
     movq   %r13, -40(%rbp)
     movq   $4433, %r12
     movq   $0, %rbx
     movq   %rbx, %r10
     movq   %r12, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $54006, %r13
     movq   $1, %r10
     movq   %r10, -56(%rbp)
     movq   -56(%rbp), %r10
     movq   %r13, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $242036, %r14
     movq   $2, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   %r14, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $399050, %r15
     movq   $3, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r15, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $242036, %r10
     movq   %r10, -104(%rbp)
     movq   $4, %r12
     movq   %r12, %r10
     movq   -104(%rbp), %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $54006, %r10
     movq   %r10, -120(%rbp)
     movq   $5, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   -120(%rbp), %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $4433, %rbx
     movq   $6, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, unsharpKernel(, %r10, 8)
     movq   $3, %r14
     movq   %r14, %r10
     movq   %r10, -160(%rbp)
     movq   $0, %r15
     movq   %r15, %r13
     movq   %r15, %r10
     movq   %r10, kernel_sum(%rip)
     movq   $0, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r12
createKernel_FOR_COND_L28:
     movq   $2, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   -160(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -184(%rbp)
     movq   $1, %r10
     movq   %r10, -192(%rbp)
     movq   -192(%rbp), %r10
     movq   -184(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -200(%rbp)
     movq   -200(%rbp), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    createKernel_FOR_L36
     jmp    createKernel_END_FOR_L43
createKernel_FOR_L36:
     movq   %r12, %rax
     movq   unsharpKernel( ,%rax, 8), %rax
     movq   %rax, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   kernel_sum(%rip), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r13
     movq   %r15, %r10
     movq   %r10, kernel_sum(%rip)
     movq   $1, %rbx
     movq   %rbx, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r12
     jmp    createKernel_FOR_COND_L28
createKernel_END_FOR_L43:
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %r15
     movq   -24(%rbp), %rbx
     movq   -32(%rbp), %r12
     movq   -40(%rbp), %r13
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
convert2RGB:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*213), %rsp
     movq   %r15, -16(%rbp)
     movq   %r14, -24(%rbp)
     movq   %rbx, -32(%rbp)
     movq   %r13, -40(%rbp)
convert2RGB_convert0RGB:
     movq   $0, %r10
     movq   %r10, -48(%rbp)
     movq   -48(%rbp), %r13
convert2RGB_FOR_COND_L3:
     movq   rows(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_FOR_L7
     jmp    convert2RGB_END_FOR_L287
convert2RGB_FOR_L7:
     movq   $0, %r15
     movq   %r15, %r12
convert2RGB_FOR_COND_L10:
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_FOR_L14
     jmp    convert2RGB_END_FOR_L282
convert2RGB_FOR_L14:
     movq   $0, %r10
     movq   %r10, -56(%rbp)
     movq   -56(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   $0, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %rbx
     movq   $0, %r10
     movq   %r10, -80(%rbp)
     movq   -80(%rbp), %r15
     movq   $0, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r10, -96(%rbp)
     movq   $0, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r14
     movq   $0, %r10
     movq   %r10, -112(%rbp)
     movq   -112(%rbp), %r10
     movq   %r10, -120(%rbp)
     movq   $0, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   %r10, -136(%rbp)
     movq   $0, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   %r10, -152(%rbp)
     movq   $0, %r10
     movq   %r10, -160(%rbp)
     movq   -160(%rbp), %r10
     movq   %r10, -168(%rbp)
     movq   $0, %r10
     movq   %r10, -176(%rbp)
     movq   $2193, %r10
     movq   %r10, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -192(%rbp)
     movq   $3, %r10
     movq   %r10, -200(%rbp)
     movq   -200(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   -192(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $1, %r10
     movq   %r10, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -232(%rbp)
     movq   -176(%rbp), %r10
     movq   -232(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -240(%rbp)
     movq   -240(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L222
     jmp    convert2RGB_ELSE_IF_L44
convert2RGB_ELSE_IF_L44:
     movq   $2193, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -256(%rbp)
     movq   $3, %r10
     movq   %r10, -264(%rbp)
     movq   -264(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -272(%rbp)
     movq   -272(%rbp), %r10
     movq   -256(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -280(%rbp)
     movq   $0, %r10
     movq   %r10, -288(%rbp)
     movq   -288(%rbp), %r10
     movq   -280(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -296(%rbp)
     movq   $60, %r10
     movq   %r10, -304(%rbp)
     movq   -296(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -312(%rbp)
     movq   -304(%rbp), %r10
     movq   -312(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -320(%rbp)
     movq   $6, %r10
     movq   %r10, -328(%rbp)
     movq   -328(%rbp), %r10
     movq   -320(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rdx, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   -336(%rbp), %r14
     movq   $2193, %r10
     movq   %r10, -344(%rbp)
     movq   -344(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -352(%rbp)
     movq   $3, %r10
     movq   %r10, -360(%rbp)
     movq   -360(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -368(%rbp)
     movq   -368(%rbp), %r10
     movq   -352(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -376(%rbp)
     movq   $0, %r10
     movq   %r10, -384(%rbp)
     movq   -384(%rbp), %r10
     movq   -376(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -392(%rbp)
     movq   $60, %r10
     movq   %r10, -400(%rbp)
     movq   -392(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -408(%rbp)
     movq   -400(%rbp), %r10
     movq   -408(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rdx, %r11
     movq   %r11, %r10
     movq   %r10, -416(%rbp)
     movq   -416(%rbp), %r10
     movq   %r10, -120(%rbp)
     movq   $2193, %r10
     movq   %r10, -424(%rbp)
     movq   -424(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -432(%rbp)
     movq   $3, %r10
     movq   %r10, -440(%rbp)
     movq   -440(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -448(%rbp)
     movq   -448(%rbp), %r10
     movq   -432(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -456(%rbp)
     movq   $2, %r10
     movq   %r10, -464(%rbp)
     movq   -464(%rbp), %r10
     movq   -456(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -472(%rbp)
     movq   $1024, %r10
     movq   %r10, -480(%rbp)
     movq   $2193, %r10
     movq   %r10, -488(%rbp)
     movq   -488(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -496(%rbp)
     movq   $3, %r10
     movq   %r10, -504(%rbp)
     movq   -504(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -512(%rbp)
     movq   -512(%rbp), %r10
     movq   -496(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -520(%rbp)
     movq   $1, %r10
     movq   %r10, -528(%rbp)
     movq   -528(%rbp), %r10
     movq   -520(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -536(%rbp)
     movq   -536(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -544(%rbp)
     movq   -544(%rbp), %r10
     movq   -480(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -552(%rbp)
     movq   -472(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -560(%rbp)
     movq   -552(%rbp), %r10
     movq   -560(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -568(%rbp)
     movq   $1024, %r10
     movq   %r10, -576(%rbp)
     movq   $4, %r10
     movq   %r10, -584(%rbp)
     movq   -584(%rbp), %r10
     movq   -576(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -592(%rbp)
     movq   -592(%rbp), %r10
     movq   -568(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -600(%rbp)
     movq   -600(%rbp), %r10
     movq   %r10, -136(%rbp)
     movq   $2193, %r10
     movq   %r10, -608(%rbp)
     movq   -608(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -616(%rbp)
     movq   $3, %r10
     movq   %r10, -624(%rbp)
     movq   -624(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -632(%rbp)
     movq   -632(%rbp), %r10
     movq   -616(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -640(%rbp)
     movq   $2, %r10
     movq   %r10, -648(%rbp)
     movq   -648(%rbp), %r10
     movq   -640(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -656(%rbp)
     movq   $1024, %r10
     movq   %r10, -664(%rbp)
     movq   $60, %r10
     movq   %r10, -672(%rbp)
     movq   -672(%rbp), %r10
     movq   -664(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -680(%rbp)
     movq   $2193, %r10
     movq   %r10, -688(%rbp)
     movq   -688(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -696(%rbp)
     movq   $3, %r10
     movq   %r10, -704(%rbp)
     movq   -704(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -712(%rbp)
     movq   -712(%rbp), %r10
     movq   -696(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -720(%rbp)
     movq   $1, %r10
     movq   %r10, -728(%rbp)
     movq   -728(%rbp), %r10
     movq   -720(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -736(%rbp)
     movq   -736(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -744(%rbp)
     movq   -120(%rbp), %r10
     movq   -744(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -752(%rbp)
     movq   -752(%rbp), %r10
     movq   -680(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -760(%rbp)
     movq   -656(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -768(%rbp)
     movq   -760(%rbp), %r10
     movq   -768(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -776(%rbp)
     movq   $1024, %r10
     movq   %r10, -784(%rbp)
     movq   $60, %r10
     movq   %r10, -792(%rbp)
     movq   -792(%rbp), %r10
     movq   -784(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -800(%rbp)
     movq   $4, %r10
     movq   %r10, -808(%rbp)
     movq   -808(%rbp), %r10
     movq   -800(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -816(%rbp)
     movq   -816(%rbp), %r10
     movq   -776(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -824(%rbp)
     movq   -824(%rbp), %r10
     movq   %r10, -152(%rbp)
     movq   $2193, %r10
     movq   %r10, -832(%rbp)
     movq   -832(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -840(%rbp)
     movq   $3, %r10
     movq   %r10, -848(%rbp)
     movq   -848(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -856(%rbp)
     movq   -856(%rbp), %r10
     movq   -840(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -864(%rbp)
     movq   $2, %r10
     movq   %r10, -872(%rbp)
     movq   -872(%rbp), %r10
     movq   -864(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -880(%rbp)
     movq   $1024, %r10
     movq   %r10, -888(%rbp)
     movq   $60, %r10
     movq   %r10, -896(%rbp)
     movq   -896(%rbp), %r10
     movq   -888(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -904(%rbp)
     movq   $2193, %r10
     movq   %r10, -912(%rbp)
     movq   -912(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -920(%rbp)
     movq   $3, %r10
     movq   %r10, -928(%rbp)
     movq   -928(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -936(%rbp)
     movq   -936(%rbp), %r10
     movq   -920(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -944(%rbp)
     movq   $1, %r10
     movq   %r10, -952(%rbp)
     movq   -952(%rbp), %r10
     movq   -944(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -960(%rbp)
     movq   $60, %r10
     movq   %r10, -968(%rbp)
     movq   -120(%rbp), %r10
     movq   -968(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -976(%rbp)
     movq   -960(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -984(%rbp)
     movq   -976(%rbp), %r10
     movq   -984(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -992(%rbp)
     movq   -992(%rbp), %r10
     movq   -904(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1000(%rbp)
     movq   -880(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1008(%rbp)
     movq   -1000(%rbp), %r10
     movq   -1008(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1016(%rbp)
     movq   $1024, %r10
     movq   %r10, -1024(%rbp)
     movq   $60, %r10
     movq   %r10, -1032(%rbp)
     movq   -1032(%rbp), %r10
     movq   -1024(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1040(%rbp)
     movq   $4, %r10
     movq   %r10, -1048(%rbp)
     movq   -1048(%rbp), %r10
     movq   -1040(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1056(%rbp)
     movq   -1056(%rbp), %r10
     movq   -1016(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1064(%rbp)
     movq   -1064(%rbp), %r10
     movq   %r10, -168(%rbp)
     movq   $2193, %r10
     movq   %r10, -1072(%rbp)
     movq   -1072(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1080(%rbp)
     movq   $3, %r10
     movq   %r10, -1088(%rbp)
     movq   -1088(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1096(%rbp)
     movq   -1096(%rbp), %r10
     movq   -1080(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1104(%rbp)
     movq   $2, %r10
     movq   %r10, -1112(%rbp)
     movq   -1112(%rbp), %r10
     movq   -1104(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1120(%rbp)
     movq   $4, %r10
     movq   %r10, -1128(%rbp)
     movq   -1120(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1136(%rbp)
     movq   -1128(%rbp), %r10
     movq   -1136(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1144(%rbp)
     movq   -1144(%rbp), %r10
     movq   %r10, -96(%rbp)
     movq   $0, %r10
     movq   %r10, -1152(%rbp)
     movq   -1152(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1160(%rbp)
     movq   -1160(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L159
     jmp    convert2RGB_END_IF_L163
convert2RGB_IF_L159:
     movq   -96(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   -168(%rbp), %rbx
     movq   -136(%rbp), %r15
convert2RGB_END_IF_L163:
     movq   $1, %r10
     movq   %r10, -1168(%rbp)
     movq   -1168(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1176(%rbp)
     movq   -1176(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L168
     jmp    convert2RGB_END_IF_L172
convert2RGB_IF_L168:
     movq   -152(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   -96(%rbp), %rbx
     movq   -136(%rbp), %r15
convert2RGB_END_IF_L172:
     movq   $2, %r10
     movq   %r10, -1184(%rbp)
     movq   -1184(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1192(%rbp)
     movq   -1192(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L177
     jmp    convert2RGB_END_IF_L181
convert2RGB_IF_L177:
     movq   -136(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   -96(%rbp), %rbx
     movq   -168(%rbp), %r15
convert2RGB_END_IF_L181:
     movq   $3, %r10
     movq   %r10, -1200(%rbp)
     movq   -1200(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1208(%rbp)
     movq   -1208(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L186
     jmp    convert2RGB_END_IF_L190
convert2RGB_IF_L186:
     movq   -136(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   -152(%rbp), %rbx
     movq   -96(%rbp), %r15
convert2RGB_END_IF_L190:
     movq   $4, %r10
     movq   %r10, -1216(%rbp)
     movq   -1216(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1224(%rbp)
     movq   -1224(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L195
     jmp    convert2RGB_END_IF_L199
convert2RGB_IF_L195:
     movq   -168(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   -136(%rbp), %rbx
     movq   -96(%rbp), %r15
convert2RGB_END_IF_L199:
     movq   $5, %r10
     movq   %r10, -1232(%rbp)
     movq   -1232(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1240(%rbp)
     movq   -1240(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L204
     jmp    convert2RGB_END_IF_L208
convert2RGB_IF_L204:
     movq   -96(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   -136(%rbp), %rbx
     movq   -152(%rbp), %r15
convert2RGB_END_IF_L208:
     movq   $0, %r10
     movq   %r10, -1248(%rbp)
     movq   -1248(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1256(%rbp)
     movq   -1256(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2RGB_IF_L213
     jmp    convert2RGB_END_IF_L220
convert2RGB_IF_L213:
     movq   $0, %r10
     movq   %r10, -1264(%rbp)
     movq   -1264(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   $0, %r10
     movq   %r10, -1272(%rbp)
     movq   -1272(%rbp), %rbx
     movq   $0, %r10
     movq   %r10, -1280(%rbp)
     movq   -1280(%rbp), %r15
convert2RGB_END_IF_L220:
     jmp    convert2RGB_END_IF_L253
convert2RGB_IF_L222:
     movq   $2193, %r10
     movq   %r10, -1288(%rbp)
     movq   -1288(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1296(%rbp)
     movq   $3, %r14
     movq   %r14, %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1304(%rbp)
     movq   -1304(%rbp), %r10
     movq   -1296(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1312(%rbp)
     movq   $2, %r10
     movq   %r10, -1320(%rbp)
     movq   -1320(%rbp), %r10
     movq   -1312(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1328(%rbp)
     movq   $4, %r10
     movq   %r10, -1336(%rbp)
     movq   -1328(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1344(%rbp)
     movq   -1336(%rbp), %r10
     movq   -1344(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1352(%rbp)
     movq   -1352(%rbp), %r10
     movq   %r10, -64(%rbp)
     movq   $2193, %r10
     movq   %r10, -1360(%rbp)
     movq   -1360(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1368(%rbp)
     movq   $3, %r10
     movq   %r10, -1376(%rbp)
     movq   -1376(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1384(%rbp)
     movq   -1384(%rbp), %r10
     movq   -1368(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -1392(%rbp)
     movq   -1392(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1400(%rbp)
     movq   $4, %r10
     movq   %r10, -1408(%rbp)
     movq   -1400(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1416(%rbp)
     movq   -1408(%rbp), %r10
     movq   -1416(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1424(%rbp)
     movq   -1424(%rbp), %rbx
     movq   $2193, %r10
     movq   %r10, -1432(%rbp)
     movq   -1432(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1440(%rbp)
     movq   $3, %r10
     movq   %r10, -1448(%rbp)
     movq   -1448(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1456(%rbp)
     movq   -1456(%rbp), %r10
     movq   -1440(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1464(%rbp)
     movq   $2, %r10
     movq   %r10, -1472(%rbp)
     movq   -1472(%rbp), %r10
     movq   -1464(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1480(%rbp)
     movq   $4, %r14
     movq   -1480(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1488(%rbp)
     movq   %r14, %r10
     movq   -1488(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1496(%rbp)
     movq   -1496(%rbp), %r15
convert2RGB_END_IF_L253:
     movq   $2193, %r10
     movq   %r10, -1504(%rbp)
     movq   -1504(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1512(%rbp)
     movq   $3, %r10
     movq   %r10, -1520(%rbp)
     movq   -1520(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1528(%rbp)
     movq   -1528(%rbp), %r10
     movq   -1512(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1536(%rbp)
     movq   $0, %r10
     movq   %r10, -1544(%rbp)
     movq   -1544(%rbp), %r10
     movq   -1536(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1552(%rbp)
     movq   -1552(%rbp), %r10
     movq   -64(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -1568(%rbp)
     movq   -1568(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -1576(%rbp)
     movq   -1576(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1584(%rbp)
     movq   -1584(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1592(%rbp)
     movq   $1, %r10
     movq   %r10, -1600(%rbp)
     movq   -1600(%rbp), %r10
     movq   -1592(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1608(%rbp)
     movq   -1608(%rbp), %r10
     movq   %rbx, %r11
     movq   %r11, image(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -1624(%rbp)
     movq   -1624(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1632(%rbp)
     movq   $3, %r10
     movq   %r10, -1640(%rbp)
     movq   -1640(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1648(%rbp)
     movq   -1648(%rbp), %r10
     movq   -1632(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1656(%rbp)
     movq   $2, %r10
     movq   %r10, -1664(%rbp)
     movq   -1664(%rbp), %r10
     movq   -1656(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1672(%rbp)
     movq   -1672(%rbp), %r10
     movq   %r15, %r11
     movq   %r11, image(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -1688(%rbp)
     movq   -1688(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r12
     jmp    convert2RGB_FOR_COND_L10
convert2RGB_END_FOR_L282:
     movq   $1, %r10
     movq   %r10, -1696(%rbp)
     movq   -1696(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r13
     jmp    convert2RGB_FOR_COND_L3
convert2RGB_END_FOR_L287:
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r15
     movq   -24(%rbp), %r14
     movq   -32(%rbp), %rbx
     movq   -40(%rbp), %r13
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
convert2HSV:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*255), %rsp
     movq   %r15, -16(%rbp)
     movq   %rbx, -24(%rbp)
     movq   %r14, -32(%rbp)
     movq   %r13, -40(%rbp)
convert2HSV_convert0HSV:
     movq   $0, %rbx
     movq   %rbx, %r13
convert2HSV_FOR_COND_L3:
     movq   rows(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r14
     movq   %r14, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_FOR_L7
     jmp    convert2HSV_END_FOR_L341
convert2HSV_FOR_L7:
     movq   $0, %r15
     movq   %r15, %r12
convert2HSV_FOR_COND_L10:
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -48(%rbp)
     movq   -48(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_FOR_L14
     jmp    convert2HSV_END_FOR_L336
convert2HSV_FOR_L14:
     movq   $0, %r10
     movq   %r10, -56(%rbp)
     movq   -56(%rbp), %rbx
     movq   $0, %r10
     movq   %r10, -64(%rbp)
     movq   -64(%rbp), %r14
     movq   $0, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   %r10, -80(%rbp)
     movq   $0, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r15
     movq   $0, %r10
     movq   %r10, -96(%rbp)
     movq   -96(%rbp), %r10
     movq   %r10, -104(%rbp)
     movq   $0, %r10
     movq   %r10, -112(%rbp)
     movq   -112(%rbp), %r10
     movq   %r10, -120(%rbp)
     movq   $2193, %r10
     movq   %r10, -128(%rbp)
     movq   -128(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $1, %r10
     movq   %r10, -152(%rbp)
     movq   -152(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -160(%rbp)
     movq   $2193, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -192(%rbp)
     movq   $0, %r10
     movq   %r10, -200(%rbp)
     movq   -200(%rbp), %r10
     movq   -192(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -216(%rbp)
     movq   -160(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -224(%rbp)
     movq   -224(%rbp), %r10
     movq   -216(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovg  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -232(%rbp)
     movq   -232(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L62
     jmp    convert2HSV_ELSE_IF_L44
convert2HSV_ELSE_IF_L44:
     movq   $2193, %r10
     movq   %r10, -240(%rbp)
     movq   -240(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -264(%rbp)
     movq   $1, %r10
     movq   %r10, -272(%rbp)
     movq   -272(%rbp), %r10
     movq   -264(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -280(%rbp)
     movq   -280(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -288(%rbp)
     movq   -288(%rbp), %r14
     movq   $2193, %r10
     movq   %r10, -296(%rbp)
     movq   -296(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -304(%rbp)
     movq   $3, %r10
     movq   %r10, -312(%rbp)
     movq   -312(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -320(%rbp)
     movq   -320(%rbp), %r10
     movq   -304(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $0, %r10
     movq   %r10, -328(%rbp)
     movq   -328(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -336(%rbp)
     movq   -336(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -344(%rbp)
     movq   -344(%rbp), %rbx
     jmp    convert2HSV_END_IF_L79
convert2HSV_IF_L62:
     movq   $2193, %r10
     movq   %r10, -352(%rbp)
     movq   -352(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -360(%rbp)
     movq   $3, %r10
     movq   %r10, -368(%rbp)
     movq   -368(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -376(%rbp)
     movq   -376(%rbp), %r10
     movq   -360(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -384(%rbp)
     movq   $0, %r10
     movq   %r10, -392(%rbp)
     movq   -392(%rbp), %r10
     movq   -384(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -400(%rbp)
     movq   -400(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -408(%rbp)
     movq   -408(%rbp), %r14
     movq   $2193, %r10
     movq   %r10, -416(%rbp)
     movq   -416(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -424(%rbp)
     movq   -424(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -432(%rbp)
     movq   -432(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -440(%rbp)
     movq   $1, %r10
     movq   %r10, -448(%rbp)
     movq   -448(%rbp), %r10
     movq   -440(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -456(%rbp)
     movq   -456(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -464(%rbp)
     movq   -464(%rbp), %rbx
convert2HSV_END_IF_L79:
     movq   $2193, %r10
     movq   %r10, -472(%rbp)
     movq   -472(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -480(%rbp)
     movq   $3, %r10
     movq   %r10, -488(%rbp)
     movq   -488(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -496(%rbp)
     movq   -496(%rbp), %r10
     movq   -480(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -504(%rbp)
     movq   $2, %r10
     movq   %r10, -512(%rbp)
     movq   -512(%rbp), %r10
     movq   -504(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -520(%rbp)
     movq   -520(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -528(%rbp)
     movq   -528(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -536(%rbp)
     movq   -536(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L112
     jmp    convert2HSV_ELSE_IF_L90
convert2HSV_ELSE_IF_L90:
     movq   $2193, %r10
     movq   %r10, -544(%rbp)
     movq   -544(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -552(%rbp)
     movq   $3, %r10
     movq   %r10, -560(%rbp)
     movq   -560(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -568(%rbp)
     movq   -568(%rbp), %r10
     movq   -552(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $2, %r10
     movq   %r10, -576(%rbp)
     movq   -576(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -584(%rbp)
     movq   -584(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -592(%rbp)
     movq   -592(%rbp), %r10
     movq   %rbx, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovg  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -600(%rbp)
     movq   -600(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L101
     jmp    convert2HSV_END_IF_L110
convert2HSV_IF_L101:
     movq   $2193, %r10
     movq   %r10, -608(%rbp)
     movq   -608(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -616(%rbp)
     movq   $3, %r10
     movq   %r10, -624(%rbp)
     movq   -624(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -632(%rbp)
     movq   -632(%rbp), %r10
     movq   -616(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -640(%rbp)
     movq   $2, %r10
     movq   %r10, -648(%rbp)
     movq   -648(%rbp), %r10
     movq   -640(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -656(%rbp)
     movq   -656(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -664(%rbp)
     movq   -664(%rbp), %rbx
convert2HSV_END_IF_L110:
     jmp    convert2HSV_END_IF_L121
convert2HSV_IF_L112:
     movq   $2193, %r10
     movq   %r10, -672(%rbp)
     movq   -672(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -680(%rbp)
     movq   -680(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -688(%rbp)
     movq   -688(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -696(%rbp)
     movq   $2, %r10
     movq   %r10, -704(%rbp)
     movq   -704(%rbp), %r10
     movq   -696(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -712(%rbp)
     movq   -712(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -720(%rbp)
     movq   -720(%rbp), %r14
convert2HSV_END_IF_L121:
     movq   %rbx, %r10
     movq   %r14, %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -728(%rbp)
     movq   -728(%rbp), %r10
     movq   %r10, -80(%rbp)
     movq   $4, %r10
     movq   %r10, -736(%rbp)
     movq   %r14, %r10
     movq   -736(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -744(%rbp)
     movq   -744(%rbp), %r10
     movq   %r10, -120(%rbp)
     movq   $0, %r10
     movq   %r10, -752(%rbp)
     movq   -752(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -760(%rbp)
     movq   -760(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L137
     jmp    convert2HSV_ELSE_IF_L131
convert2HSV_ELSE_IF_L131:
     movq   $1024, %r15
     movq   -80(%rbp), %r10
     movq   %r15, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   %r14, %r10
     movq   %rbx, %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -768(%rbp)
     movq   -768(%rbp), %r10
     movq   %r10, -104(%rbp)
     jmp    convert2HSV_END_IF_L140
convert2HSV_IF_L137:
     movq   $0, %r10
     movq   %r10, -776(%rbp)
     movq   -776(%rbp), %r10
     movq   %r10, -104(%rbp)
convert2HSV_END_IF_L140:
     movq   $0, %r10
     movq   %r10, -784(%rbp)
     movq   -784(%rbp), %r10
     movq   -80(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -792(%rbp)
     movq   -792(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L303
     jmp    convert2HSV_ELSE_IF_L145
convert2HSV_ELSE_IF_L145:
     movq   $2193, %r10
     movq   %r10, -800(%rbp)
     movq   -800(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -808(%rbp)
     movq   -808(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -816(%rbp)
     movq   -816(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $0, %r10
     movq   %r10, -824(%rbp)
     movq   -824(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -832(%rbp)
     movq   -832(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -840(%rbp)
     movq   -840(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -848(%rbp)
     movq   $2193, %r10
     movq   %r10, -856(%rbp)
     movq   -856(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -864(%rbp)
     movq   $3, %r10
     movq   %r10, -872(%rbp)
     movq   -872(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -880(%rbp)
     movq   -880(%rbp), %r10
     movq   -864(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -888(%rbp)
     movq   $2, %r10
     movq   %r10, -896(%rbp)
     movq   -896(%rbp), %r10
     movq   -888(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -904(%rbp)
     movq   $2193, %r10
     movq   %r10, -912(%rbp)
     movq   -912(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -920(%rbp)
     movq   -920(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -928(%rbp)
     movq   -928(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $1, %r10
     movq   %r10, -936(%rbp)
     movq   -936(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -944(%rbp)
     movq   -944(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -952(%rbp)
     movq   -904(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -960(%rbp)
     movq   -960(%rbp), %r10
     movq   -952(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, %r10
     movq   %r10, -968(%rbp)
     movq   -968(%rbp), %r10
     movq   -848(%rbp), %r11
     and    %r10, %r11
     movq   %r11, %r10
     movq   %r10, -976(%rbp)
     movq   -976(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L281
     jmp    convert2HSV_ELSE_IF_L172
convert2HSV_ELSE_IF_L172:
     movq   $2193, %r10
     movq   %r10, -984(%rbp)
     movq   -984(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -992(%rbp)
     movq   -992(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1000(%rbp)
     movq   $0, %r10
     movq   %r10, -1008(%rbp)
     movq   -1008(%rbp), %r10
     movq   -1000(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1016(%rbp)
     movq   -1016(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1024(%rbp)
     movq   -1024(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1032(%rbp)
     movq   $2193, %r10
     movq   %r10, -1040(%rbp)
     movq   -1040(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1048(%rbp)
     movq   $3, %r10
     movq   %r10, -1056(%rbp)
     movq   -1056(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1064(%rbp)
     movq   -1064(%rbp), %r10
     movq   -1048(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1072(%rbp)
     movq   $2, %r10
     movq   %r10, -1080(%rbp)
     movq   -1080(%rbp), %r10
     movq   -1072(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1088(%rbp)
     movq   $2193, %r10
     movq   %r10, -1096(%rbp)
     movq   -1096(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -1104(%rbp)
     movq   -1104(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1112(%rbp)
     movq   -1112(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $1, %r10
     movq   %r10, -1120(%rbp)
     movq   -1120(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1128(%rbp)
     movq   -1128(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1136(%rbp)
     movq   -1088(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1144(%rbp)
     movq   -1144(%rbp), %r10
     movq   -1136(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1152(%rbp)
     movq   -1152(%rbp), %r10
     movq   -1032(%rbp), %r11
     and    %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1160(%rbp)
     movq   -1160(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L257
     jmp    convert2HSV_ELSE_IF_L199
convert2HSV_ELSE_IF_L199:
     movq   $2193, %r10
     movq   %r10, -1168(%rbp)
     movq   -1168(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -1176(%rbp)
     movq   -1176(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1184(%rbp)
     movq   -1184(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1192(%rbp)
     movq   $1, %r10
     movq   %r10, -1200(%rbp)
     movq   -1200(%rbp), %r10
     movq   -1192(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1208(%rbp)
     movq   -1208(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1216(%rbp)
     movq   -1216(%rbp), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmove  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1224(%rbp)
     movq   -1224(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    convert2HSV_IF_L233
     jmp    convert2HSV_ELSE_IF_L210
convert2HSV_ELSE_IF_L210:
     movq   $240, %r10
     movq   %r10, -1232(%rbp)
     movq   $60, %rbx
     movq   $2193, %r10
     movq   %r10, -1240(%rbp)
     movq   -1240(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1248(%rbp)
     movq   $3, %r10
     movq   %r10, -1256(%rbp)
     movq   -1256(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1264(%rbp)
     movq   -1264(%rbp), %r10
     movq   -1248(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $0, %r10
     movq   %r10, -1272(%rbp)
     movq   -1272(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1280(%rbp)
     movq   $2193, %r10
     movq   %r10, -1288(%rbp)
     movq   -1288(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -1296(%rbp)
     movq   -1296(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1304(%rbp)
     movq   -1304(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1312(%rbp)
     movq   $1, %r10
     movq   %r10, -1320(%rbp)
     movq   -1320(%rbp), %r10
     movq   -1312(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1328(%rbp)
     movq   -1280(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1336(%rbp)
     movq   -1328(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1344(%rbp)
     movq   -1344(%rbp), %r10
     movq   -1336(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1352(%rbp)
     movq   -1352(%rbp), %r10
     movq   %rbx, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1360(%rbp)
     movq   -80(%rbp), %r10
     movq   -1360(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1368(%rbp)
     movq   -1368(%rbp), %r10
     movq   -1232(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1376(%rbp)
     movq   -1376(%rbp), %r15
     jmp    convert2HSV_END_IF_L255
convert2HSV_IF_L233:
     movq   $120, %r10
     movq   %r10, -1384(%rbp)
     movq   $60, %r10
     movq   %r10, -1392(%rbp)
     movq   $2193, %r10
     movq   %r10, -1400(%rbp)
     movq   -1400(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1408(%rbp)
     movq   $3, %r10
     movq   %r10, -1416(%rbp)
     movq   -1416(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1424(%rbp)
     movq   -1424(%rbp), %r10
     movq   -1408(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -1432(%rbp)
     movq   -1432(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1440(%rbp)
     movq   $2193, %r10
     movq   %r10, -1448(%rbp)
     movq   -1448(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -1456(%rbp)
     movq   -1456(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1464(%rbp)
     movq   -1464(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1472(%rbp)
     movq   $0, %r10
     movq   %r10, -1480(%rbp)
     movq   -1480(%rbp), %r10
     movq   -1472(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1488(%rbp)
     movq   -1440(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1496(%rbp)
     movq   -1488(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1504(%rbp)
     movq   -1504(%rbp), %r10
     movq   -1496(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1512(%rbp)
     movq   -1512(%rbp), %r10
     movq   -1392(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1520(%rbp)
     movq   -80(%rbp), %r10
     movq   -1520(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1528(%rbp)
     movq   -1528(%rbp), %r10
     movq   -1384(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1536(%rbp)
     movq   -1536(%rbp), %r15
convert2HSV_END_IF_L255:
     jmp    convert2HSV_END_IF_L279
convert2HSV_IF_L257:
     movq   $360, %r10
     movq   %r10, -1544(%rbp)
     movq   $60, %r10
     movq   %r10, -1552(%rbp)
     movq   $2193, %r10
     movq   %r10, -1560(%rbp)
     movq   -1560(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -1568(%rbp)
     movq   -1568(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1576(%rbp)
     movq   -1576(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $1, %r10
     movq   %r10, -1584(%rbp)
     movq   -1584(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1592(%rbp)
     movq   $2193, %r10
     movq   %r10, -1600(%rbp)
     movq   -1600(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1608(%rbp)
     movq   $3, %r10
     movq   %r10, -1616(%rbp)
     movq   -1616(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1624(%rbp)
     movq   -1624(%rbp), %r10
     movq   -1608(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1632(%rbp)
     movq   $2, %r10
     movq   %r10, -1640(%rbp)
     movq   -1640(%rbp), %r10
     movq   -1632(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1648(%rbp)
     movq   -1592(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1656(%rbp)
     movq   -1648(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1664(%rbp)
     movq   -1664(%rbp), %r10
     movq   -1656(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1672(%rbp)
     movq   -1672(%rbp), %r10
     movq   -1552(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   -80(%rbp), %r10
     movq   %r14, %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1680(%rbp)
     movq   -1680(%rbp), %r10
     movq   -1544(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1688(%rbp)
     movq   -1688(%rbp), %r15
convert2HSV_END_IF_L279:
     jmp    convert2HSV_END_IF_L301
convert2HSV_IF_L281:
     movq   $60, %rbx
     movq   $2193, %r10
     movq   %r10, -1696(%rbp)
     movq   -1696(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1704(%rbp)
     movq   $3, %r10
     movq   %r10, -1712(%rbp)
     movq   -1712(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1720(%rbp)
     movq   -1720(%rbp), %r10
     movq   -1704(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1728(%rbp)
     movq   $1, %r10
     movq   %r10, -1736(%rbp)
     movq   -1736(%rbp), %r10
     movq   -1728(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1744(%rbp)
     movq   $2193, %r10
     movq   %r10, -1752(%rbp)
     movq   -1752(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -1760(%rbp)
     movq   -1760(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1768(%rbp)
     movq   -1768(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1776(%rbp)
     movq   $2, %r10
     movq   %r10, -1784(%rbp)
     movq   -1784(%rbp), %r10
     movq   -1776(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1792(%rbp)
     movq   -1744(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1800(%rbp)
     movq   -1792(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -1808(%rbp)
     movq   -1808(%rbp), %r10
     movq   -1800(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1816(%rbp)
     movq   -1816(%rbp), %r10
     movq   %rbx, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1824(%rbp)
     movq   -80(%rbp), %r10
     movq   -1824(%rbp), %r11
     movq   %r11, %rax
     cqo     
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, %r10
     movq   %r10, -1832(%rbp)
     movq   -1832(%rbp), %r15
convert2HSV_END_IF_L301:
     jmp    convert2HSV_END_IF_L307
convert2HSV_IF_L303:
     movq   $1, %r10
     movq   %r10, -1840(%rbp)
     movq   -1840(%rbp), %r10
     neg    %r10
     movq   %r10, %r10
     movq   %r10, -1848(%rbp)
     movq   -1848(%rbp), %r15
convert2HSV_END_IF_L307:
     movq   $2193, %r10
     movq   %r10, -1856(%rbp)
     movq   -1856(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -1864(%rbp)
     movq   -1864(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1872(%rbp)
     movq   -1872(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $0, %r10
     movq   %r10, -1880(%rbp)
     movq   -1880(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1888(%rbp)
     movq   -1888(%rbp), %r10
     movq   %r15, %r11
     movq   %r11, image(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -1904(%rbp)
     movq   -1904(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1912(%rbp)
     movq   $3, %r10
     movq   %r10, -1920(%rbp)
     movq   -1920(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1928(%rbp)
     movq   -1928(%rbp), %r10
     movq   -1912(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1936(%rbp)
     movq   $1, %r10
     movq   %r10, -1944(%rbp)
     movq   -1944(%rbp), %r10
     movq   -1936(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1952(%rbp)
     movq   -1952(%rbp), %r10
     movq   -104(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $2193, %r10
     movq   %r10, -1968(%rbp)
     movq   -1968(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -1976(%rbp)
     movq   -1976(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -1984(%rbp)
     movq   -1984(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r15
     movq   $2, %r10
     movq   %r10, -1992(%rbp)
     movq   -1992(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -2000(%rbp)
     movq   -2000(%rbp), %r10
     movq   -120(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -2016(%rbp)
     movq   -2016(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r12
     jmp    convert2HSV_FOR_COND_L10
convert2HSV_END_FOR_L336:
     movq   $1, %r10
     movq   %r10, -2024(%rbp)
     movq   -2024(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -2032(%rbp)
     movq   -2032(%rbp), %r13
     jmp    convert2HSV_FOR_COND_L3
convert2HSV_END_FOR_L341:
     movq   -8(%rbp), %r12
     movq   -16(%rbp), %r15
     movq   -24(%rbp), %rbx
     movq   -32(%rbp), %r14
     movq   -40(%rbp), %r13
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
write_file:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*29), %rsp
     movq   %r14, -16(%rbp)
     movq   %r12, -24(%rbp)
     movq   %rbx, -32(%rbp)
     movq   %r15, -40(%rbp)
     movq   $.STR_0, %rdi
     movq   cols(%rip), %rsi
     movq   rows(%rip), %rdx
     movq   $0, %rax
     call   ppm_open_for_write
     movq   %rax, %r15
     movq   $0, %r14
     movq   %r14, %r13
write_file_FOR_COND_L4:
     movq   rows(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -48(%rbp)
     movq   -48(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    write_file_FOR_L8
     jmp    write_file_END_FOR_L45
write_file_FOR_L8:
     movq   $0, %rbx
     movq   %rbx, %r12
write_file_FOR_COND_L11:
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -56(%rbp)
     movq   -56(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    write_file_FOR_L15
     jmp    write_file_END_FOR_L40
write_file_FOR_L15:
     movq   $2193, %r15
     movq   %r15, %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r14
     movq   $3, %r10
     movq   %r10, -64(%rbp)
     movq   -64(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -80(%rbp)
     movq   $2193, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %rbx
     movq   $3, %r10
     movq   %r10, -96(%rbp)
     movq   -96(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -112(%rbp)
     movq   $1, %r10
     movq   %r10, -120(%rbp)
     movq   -120(%rbp), %r10
     movq   -112(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -128(%rbp)
     movq   $2193, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -144(%rbp)
     movq   -144(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -152(%rbp)
     movq   -152(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -160(%rbp)
     movq   -160(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -168(%rbp)
     movq   -80(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   %r10, %rdi
     movq   -128(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -184(%rbp)
     movq   -184(%rbp), %r10
     movq   %r10, %rsi
     movq   -168(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -192(%rbp)
     movq   -192(%rbp), %r10
     movq   %r10, %rdx
     movq   $0, %rax
     call   ppm_write_next_pixel
     movq   %rax, %r10
     movq   %r10, -200(%rbp)
     movq   $1, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r12
     jmp    write_file_FOR_COND_L11
write_file_END_FOR_L40:
     movq   $1, %r10
     movq   %r10, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %r13
     jmp    write_file_FOR_COND_L4
write_file_END_FOR_L45:
     movq   $0, %rax
     call   ppm_close
     movq   %rax, %r15
     movq   -8(%rbp), %r13
     movq   -16(%rbp), %r14
     movq   -24(%rbp), %r12
     movq   -32(%rbp), %rbx
     movq   -40(%rbp), %r15
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
     
read_file:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp
     subq   $(8*34), %rsp
     movq   %r15, -16(%rbp)
     movq   %rbx, -24(%rbp)
     movq   %r12, -32(%rbp)
     movq   %r13, -40(%rbp)
     movq   $.STR_1, %rdi
     movq   $0, %rax
     call   ppm_open_for_read
     movq   %rax, %r14
     movq   $0, %rax
     call   ppm_get_cols
     movq   %rax, %r12
     movq   %r12, %r10
     movq   %r10, -48(%rbp)
     movq   %r12, %r10
     movq   %r10, cols(%rip)
     movq   $0, %rax
     call   ppm_get_rows
     movq   %rax, %rbx
     movq   %rbx, %r10
     movq   %r10, -56(%rbp)
     movq   %rbx, %r10
     movq   %r10, rows(%rip)
     movq   $0, %r10
     movq   %r10, -64(%rbp)
     movq   -64(%rbp), %r13
read_file_FOR_COND_L8:
     movq   rows(%rip), %r10
     movq   %r13, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r15
     movq   %r15, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    read_file_FOR_L12
     jmp    read_file_END_FOR_L56
read_file_FOR_L12:
     movq   $0, %r14
     movq   %r14, %r12
read_file_FOR_COND_L15:
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -72(%rbp)
     movq   -72(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    read_file_FOR_L19
     jmp    read_file_END_FOR_L51
read_file_FOR_L19:
     movq   $0, %rax
     call   ppm_get_next_pixel_color
     movq   %rax, %r10
     movq   %r10, -80(%rbp)
     movq   $2193, %rbx
     movq   %rbx, %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -88(%rbp)
     movq   -88(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -96(%rbp)
     movq   -96(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $0, %r10
     movq   %r10, -104(%rbp)
     movq   -104(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -112(%rbp)
     movq   -112(%rbp), %r10
     movq   -80(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $0, %rax
     call   ppm_get_next_pixel_color
     movq   %rax, %r10
     movq   %r10, -128(%rbp)
     movq   $2193, %r10
     movq   %r10, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -144(%rbp)
     movq   $3, %r10
     movq   %r10, -152(%rbp)
     movq   -152(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -160(%rbp)
     movq   -160(%rbp), %r10
     movq   -144(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   $1, %r10
     movq   %r10, -168(%rbp)
     movq   -168(%rbp), %r10
     movq   %rbx, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -176(%rbp)
     movq   -176(%rbp), %r10
     movq   -128(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $0, %rax
     call   ppm_get_next_pixel_color
     movq   %rax, %r10
     movq   %r10, -192(%rbp)
     movq   $2193, %r10
     movq   %r10, -200(%rbp)
     movq   -200(%rbp), %r10
     movq   %r13, %r11
     imulq  %r10, %r11
     movq   %r11, %r15
     movq   $3, %r10
     movq   %r10, -208(%rbp)
     movq   -208(%rbp), %r10
     movq   %r12, %r11
     imulq  %r10, %r11
     movq   %r11, %r10
     movq   %r10, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %r14
     movq   $2, %r10
     movq   %r10, -224(%rbp)
     movq   -224(%rbp), %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -232(%rbp)
     movq   -232(%rbp), %r10
     movq   -192(%rbp), %r11
     movq   %r11, image(, %r10, 8)
     movq   $1, %r10
     movq   %r10, -248(%rbp)
     movq   -248(%rbp), %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %rbx
     movq   %rbx, %r12
     jmp    read_file_FOR_COND_L15
read_file_END_FOR_L51:
     movq   $1, %r10
     movq   %r10, -256(%rbp)
     movq   -256(%rbp), %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r10
     movq   %r10, -264(%rbp)
     movq   -264(%rbp), %r13
     jmp    read_file_FOR_COND_L8
read_file_END_FOR_L56:
     movq   $0, %rax
     call   ppm_close
     movq   %rax, %r15
     movq   -8(%rbp), %r14
     movq   -16(%rbp), %r15
     movq   -24(%rbp), %rbx
     movq   -32(%rbp), %r12
     movq   -40(%rbp), %r13
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  
