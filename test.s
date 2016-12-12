     .comm  image, 16000000, 64
     .comm  size, 8, 8
     .comm  rows, 8, 8
     .comm  cols, 8, 8
     .globl main

.STR_0:
     .string "segovia_philbin.pgm"
.STR_1:
     .string "segovia.pgm"

main:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*9), %rsp
     # generating code for #_t0 = read()
     movq   $0, %rax
     call   read
     movq   %rax, -8(%rbp)

     # generating code for #_t1 = start_caliper()
     movq   $0, %rax
     call   start_caliper
     movq   %rax, -16(%rbp)

     # generating code for #_t2 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -24(%rbp)

     # generating code for #_t3 = cols / #_t2
     movq   -24(%rbp), %r10
     movq   cols(%rip), %r11
     movq   %r11, %rax
     cqo
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, -32(%rbp)

     # generating code for cols = #_t3
     movq   -32(%rbp), %r10
     movq   %r10, cols(%rip)

     # generating code for #_t4 = philbin()
     movq   $0, %rax
     call   philbin
     movq   %rax, -48(%rbp)

     # generating code for #_t5 = end_caliper()
     movq   $0, %rax
     call   end_caliper
     movq   %rax, -56(%rbp)

     # generating code for #_t6 = write()
     movq   $0, %rax
     call   write
     movq   %rax, -64(%rbp)

     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc


philbin:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*79), %rsp
     # generating code for #_t0 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -8(%rbp)

     # generating code for row = #_t0
     movq   -8(%rbp), %r10
     movq   %r10, -16(%rbp)

     # generating code for #_t1 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -24(%rbp)

     # generating code for col = #_t1
     movq   -24(%rbp), %r10
     movq   %r10, -32(%rbp)

     # generating code for #_t2 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -40(%rbp)

     # generating code for row = #_t2
     movq   -40(%rbp), %r10
     movq   %r10, -16(%rbp)

philbin_FOR_COND_L6:
     # generating code for #_t3 = row < rows
     movq   rows(%rip), %r10
     movq   -16(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, -48(%rbp)

     # generating code for if #_t3 goto FOR_L7
     movq   -48(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    philbin_FOR_L7

     # generating code for goto END_FOR_L7
     jmp    philbin_END_FOR_L7

philbin_FOR_L7:
     # generating code for #_t4 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -56(%rbp)

     # generating code for col = #_t4
     movq   -56(%rbp), %r10
     movq   %r10, -32(%rbp)

philbin_FOR_COND_L13:
     # generating code for #_t5 = col < cols
     movq   cols(%rip), %r10
     movq   -32(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, -64(%rbp)

     # generating code for if #_t5 goto FOR_L14
     movq   -64(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    philbin_FOR_L14

     # generating code for goto END_FOR_L14
     jmp    philbin_END_FOR_L14

philbin_FOR_L14:
     # generating code for #_t6 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -72(%rbp)

     # generating code for #_t7 = row * #_t6
     movq   -72(%rbp), %r10
     movq   -16(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -80(%rbp)

     # generating code for #_t8 = #_t7 * cols
     movq   cols(%rip), %r10
     movq   -80(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -88(%rbp)

     # generating code for #_t9 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -96(%rbp)

     # generating code for #_t10 = col * #_t9
     movq   -96(%rbp), %r10
     movq   -32(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -104(%rbp)

     # generating code for #_t11 = #_t8 + #_t10
     movq   -104(%rbp), %r10
     movq   -88(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -112(%rbp)

     # generating code for #_t12 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -120(%rbp)

     # generating code for #_t13 = #_t11 + #_t12
     movq   -120(%rbp), %r10
     movq   -112(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -128(%rbp)

     # generating code for r = image[#_t13]
     # array generating code for image[#_t13]
     movq   -128(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -136(%rbp)
     movq   -136(%rbp), %r10
     movq   %r10, -144(%rbp)

     # generating code for #_t14 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -152(%rbp)

     # generating code for #_t15 = row * #_t14
     movq   -152(%rbp), %r10
     movq   -16(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -160(%rbp)

     # generating code for #_t16 = #_t15 * cols
     movq   cols(%rip), %r10
     movq   -160(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -168(%rbp)

     # generating code for #_t17 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -176(%rbp)

     # generating code for #_t18 = col * #_t17
     movq   -176(%rbp), %r10
     movq   -32(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -184(%rbp)

     # generating code for #_t19 = #_t16 + #_t18
     movq   -184(%rbp), %r10
     movq   -168(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -192(%rbp)

     # generating code for #_t20 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -200(%rbp)

     # generating code for #_t21 = #_t19 + #_t20
     movq   -200(%rbp), %r10
     movq   -192(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -208(%rbp)

     # generating code for g = image[#_t21]
     # array generating code for image[#_t21]
     movq   -208(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -216(%rbp)
     movq   -216(%rbp), %r10
     movq   %r10, -224(%rbp)

     # generating code for #_t22 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -232(%rbp)

     # generating code for #_t23 = row * #_t22
     movq   -232(%rbp), %r10
     movq   -16(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -240(%rbp)

     # generating code for #_t24 = #_t23 * cols
     movq   cols(%rip), %r10
     movq   -240(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -248(%rbp)

     # generating code for #_t25 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -256(%rbp)

     # generating code for #_t26 = col * #_t25
     movq   -256(%rbp), %r10
     movq   -32(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -264(%rbp)

     # generating code for #_t27 = #_t24 + #_t26
     movq   -264(%rbp), %r10
     movq   -248(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -272(%rbp)

     # generating code for #_t28 = 2
     # generating code for 2

     movq   $2, %r10
     movq   %r10, -280(%rbp)

     # generating code for #_t29 = #_t27 + #_t28
     movq   -280(%rbp), %r10
     movq   -272(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -288(%rbp)

     # generating code for b = image[#_t29]
     # array generating code for image[#_t29]
     movq   -288(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -296(%rbp)
     movq   -296(%rbp), %r10
     movq   %r10, -304(%rbp)

     # generating code for #_t30 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -312(%rbp)

     # generating code for #_t31 = - #_t30

     # generating code for h = #_t31
     movq   -320(%rbp), %r10
     movq   %r10, -328(%rbp)

     # generating code for #_t32 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -336(%rbp)

     # generating code for s = #_t32
     movq   -336(%rbp), %r10
     movq   %r10, -344(%rbp)

     # generating code for max = r
     movq   -144(%rbp), %r10
     movq   %r10, -352(%rbp)

     # generating code for min = g
     movq   -224(%rbp), %r10
     movq   %r10, -360(%rbp)

     # generating code for #_t33 = b >= max
     movq   -352(%rbp), %r10
     movq   -304(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, -368(%rbp)

     # generating code for #_t34 = b >= max
     movq   -352(%rbp), %r10
     movq   -304(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, -376(%rbp)

     # generating code for #_t35 = #_t33 && #_t34
     movq   -376(%rbp), %r10
     movq   -368(%rbp), %r11
     and    %r10, %r11
     movq   %r11, -384(%rbp)

     # generating code for if #_t35 goto IF_L52
     movq   -384(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    philbin_IF_L52

     # generating code for goto END_IF_L52
     jmp    philbin_END_IF_L52

philbin_IF_L52:
     # generating code for max = b
     movq   -304(%rbp), %r10
     movq   %r10, -352(%rbp)

philbin_END_IF_L52:
     # generating code for #_t36 = g >= max
     movq   -352(%rbp), %r10
     movq   -224(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, -392(%rbp)

     # generating code for #_t37 = g >= max
     movq   -352(%rbp), %r10
     movq   -224(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovge %r10, %r11
     movq   %r11, -400(%rbp)

     # generating code for #_t38 = #_t36 && #_t37
     movq   -400(%rbp), %r10
     movq   -392(%rbp), %r11
     and    %r10, %r11
     movq   %r11, -408(%rbp)

     # generating code for if #_t38 goto IF_L59
     movq   -408(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    philbin_IF_L59

     # generating code for goto END_IF_L59
     jmp    philbin_END_IF_L59

philbin_IF_L59:
     # generating code for max = g
     movq   -224(%rbp), %r10
     movq   %r10, -352(%rbp)

philbin_END_IF_L59:
     # generating code for #_t39 = b <= min
     movq   -360(%rbp), %r10
     movq   -304(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovle  %r10, %r11
     movq   %r11, -416(%rbp)

     # generating code for #_t40 = b <= min
     movq   -360(%rbp), %r10
     movq   -304(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovle  %r10, %r11
     movq   %r11, -424(%rbp)

     # generating code for #_t41 = #_t39 && #_t40
     movq   -424(%rbp), %r10
     movq   -416(%rbp), %r11
     and    %r10, %r11
     movq   %r11, -432(%rbp)

     # generating code for if #_t41 goto IF_L66
     movq   -432(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    philbin_IF_L66

     # generating code for goto END_IF_L66
     jmp    philbin_END_IF_L66

philbin_IF_L66:
     # generating code for min = b
     movq   -304(%rbp), %r10
     movq   %r10, -360(%rbp)

philbin_END_IF_L66:
     # generating code for #_t42 = g <= min
     movq   -360(%rbp), %r10
     movq   -224(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovle  %r10, %r11
     movq   %r11, -440(%rbp)

     # generating code for #_t43 = g <= min
     movq   -360(%rbp), %r10
     movq   -224(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovle  %r10, %r11
     movq   %r11, -448(%rbp)

     # generating code for #_t44 = #_t42 && #_t43
     movq   -448(%rbp), %r10
     movq   -440(%rbp), %r11
     and    %r10, %r11
     movq   %r11, -456(%rbp)

     # generating code for if #_t44 goto IF_L73
     movq   -456(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    philbin_IF_L73

     # generating code for goto END_IF_L73
     jmp    philbin_END_IF_L73

philbin_IF_L73:
     # generating code for min = g
     movq   -224(%rbp), %r10
     movq   %r10, -360(%rbp)

philbin_END_IF_L73:
     # generating code for #_t45 = max - min
     movq   -360(%rbp), %r10
     movq   -352(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, -464(%rbp)

     # generating code for delta = #_t45
     movq   -464(%rbp), %r10
     movq   %r10, -472(%rbp)

     # generating code for #_t46 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -480(%rbp)

     # generating code for #_t47 = delta != #_t46
     movq   -480(%rbp), %r10
     movq   -472(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovne  %r10, %r11
     movq   %r11, -488(%rbp)

     # generating code for if #_t47 goto IF_L82
     movq   -488(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    philbin_IF_L82

     # generating code for goto END_IF_L82
     jmp    philbin_END_IF_L82

philbin_IF_L82:
     # generating code for #_t48 = 60
     # generating code for 60

     movq   $60, %r10
     movq   %r10, -496(%rbp)

     # generating code for #_t49 = g - b
     movq   -304(%rbp), %r10
     movq   -224(%rbp), %r11
     subq   %r10, %r11
     movq   %r11, -504(%rbp)

     # generating code for #_t50 = #_t48 * #_t49
     movq   -504(%rbp), %r10
     movq   -496(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -512(%rbp)

     # generating code for #_t51 = #_t50 / delta
     movq   -472(%rbp), %r10
     movq   -512(%rbp), %r11
     movq   %r11, %rax
     cqo
     idivq  %r10
     movq   %rax, %r11
     movq   %r11, -520(%rbp)

     # generating code for h = #_t51
     movq   -520(%rbp), %r10
     movq   %r10, -328(%rbp)

philbin_END_IF_L82:
     # generating code for #_t52 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -528(%rbp)

     # generating code for #_t53 = row * #_t52
     movq   -528(%rbp), %r10
     movq   -16(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -536(%rbp)

     # generating code for #_t54 = #_t53 * cols
     movq   cols(%rip), %r10
     movq   -536(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -544(%rbp)

     # generating code for #_t55 = 3
     # generating code for 3

     movq   $3, %r10
     movq   %r10, -552(%rbp)

     # generating code for #_t56 = col * #_t55
     movq   -552(%rbp), %r10
     movq   -32(%rbp), %r11
     imulq  %r10, %r11
     movq   %r11, -560(%rbp)

     # generating code for #_t57 = #_t54 + #_t56
     movq   -560(%rbp), %r10
     movq   -544(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -568(%rbp)

     # generating code for #_t58 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -576(%rbp)

     # generating code for #_t59 = #_t57 + #_t58
     movq   -576(%rbp), %r10
     movq   -568(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -584(%rbp)

     # generating code for image[#_t59]  = h
     # array generating code for image[#_t59]
     movq   -584(%rbp), %r10
     # adding something to -592(%rbp)
     movq   -328(%rbp), %r11
     movq   %r11, image(, %r10, 8)

     # generating code for #_t60 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -600(%rbp)

     # generating code for #_t61 = col + #_t60
     movq   -600(%rbp), %r10
     movq   -32(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -608(%rbp)

     # generating code for col = #_t61
     movq   -608(%rbp), %r10
     movq   %r10, -32(%rbp)

     # generating code for goto FOR_COND_L13
     jmp    philbin_FOR_COND_L13

philbin_END_FOR_L14:
     # generating code for #_t62 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -616(%rbp)

     # generating code for #_t63 = row + #_t62
     movq   -616(%rbp), %r10
     movq   -16(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -624(%rbp)

     # generating code for row = #_t63
     movq   -624(%rbp), %r10
     movq   %r10, -16(%rbp)

     # generating code for goto FOR_COND_L6
     jmp    philbin_FOR_COND_L6

philbin_END_FOR_L7:
     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc


write:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*12), %rsp
     # generating code for #_t1 = pgm_open_for_write(#str_t0,cols,rows,)
     movq   $.STR_0, %rdi
     movq   cols(%rip), %rsi
     movq   rows(%rip), %rdx
     movq   $0, %rax
     call   pgm_open_for_write
     movq   %rax, -8(%rbp)

     # generating code for #_t2 = cols * rows
     movq   rows(%rip), %r10
     movq   cols(%rip), %r11
     imulq  %r10, %r11
     movq   %r11, -16(%rbp)

     # generating code for size = #_t2
     movq   -16(%rbp), %r10
     movq   %r10, size(%rip)

     # generating code for #_t3 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -32(%rbp)

     # generating code for i = #_t3
     movq   -32(%rbp), %r10
     movq   %r10, -40(%rbp)

write_FOR_COND_L5:
     # generating code for #_t4 = i < size
     movq   size(%rip), %r10
     movq   -40(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, -48(%rbp)

     # generating code for if #_t4 goto FOR_L6
     movq   -48(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    write_FOR_L6

     # generating code for goto END_FOR_L6
     jmp    write_END_FOR_L6

write_FOR_L6:
     # generating code for #_t5 = pgm_write_next_pixel(image[i] ,)
     # array generating code for image[i]
     movq   -40(%rbp), %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -56(%rbp)
     movq   -56(%rbp), %r10
     movq   %r10, %rdi
     movq   $0, %rax
     call   pgm_write_next_pixel
     movq   %rax, -64(%rbp)

     # generating code for #_t6 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -72(%rbp)

     # generating code for #_t7 = i + #_t6
     movq   -72(%rbp), %r10
     movq   -40(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -80(%rbp)

     # generating code for i = #_t7
     movq   -80(%rbp), %r10
     movq   %r10, -40(%rbp)

     # generating code for goto FOR_COND_L5
     jmp    write_FOR_COND_L5

write_END_FOR_L6:
     # generating code for #_t8 = pgm_close()
     movq   $0, %rax
     call   pgm_close
     movq   %rax, -88(%rbp)

     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc


read:
      .cfi_startproc
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*16), %rsp
     # generating code for #_t1 = pgm_open_for_read(#str_t0,)
     movq   $.STR_1, %rdi
     movq   $0, %rax
     call   pgm_open_for_read
     movq   %rax, -8(%rbp)

     # generating code for #_t2 = pgm_get_cols()
     movq   $0, %rax
     call   pgm_get_cols
     movq   %rax, -16(%rbp)

     # generating code for cols = #_t2
     movq   -16(%rbp), %r10
     movq   %r10, cols(%rip)

     # generating code for #_t3 = pgm_get_rows()
     movq   $0, %rax
     call   pgm_get_rows
     movq   %rax, -32(%rbp)

     # generating code for rows = #_t3
     movq   -32(%rbp), %r10
     movq   %r10, rows(%rip)

     # generating code for #_t4 = cols * rows
     movq   rows(%rip), %r10
     movq   cols(%rip), %r11
     imulq  %r10, %r11
     movq   %r11, -48(%rbp)

     # generating code for size = #_t4
     movq   -48(%rbp), %r10
     movq   %r10, size(%rip)

     # generating code for #_t5 = 0
     # generating code for 0

     movq   $0, %r10
     movq   %r10, -64(%rbp)

     # generating code for i = #_t5
     movq   -64(%rbp), %r10
     movq   %r10, -72(%rbp)

read_FOR_COND_L9:
     # generating code for #_t6 = i < size
     movq   size(%rip), %r10
     movq   -72(%rbp), %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, -80(%rbp)

     # generating code for if #_t6 goto FOR_L10
     movq   -80(%rbp), %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    read_FOR_L10

     # generating code for goto END_FOR_L10
     jmp    read_END_FOR_L10

read_FOR_L10:
     # generating code for #_t7 = pgm_get_next_pixel()
     movq   $0, %rax
     call   pgm_get_next_pixel
     movq   %rax, -88(%rbp)

     # generating code for image[i]  = #_t7
     # array generating code for image[i]
     movq   -72(%rbp), %r10
     # adding something to -96(%rbp)
     movq   -88(%rbp), %r11
     movq   %r11, image(, %r10, 8)

     # generating code for #_t8 = 1
     # generating code for 1

     movq   $1, %r10
     movq   %r10, -104(%rbp)

     # generating code for #_t9 = i + #_t8
     movq   -104(%rbp), %r10
     movq   -72(%rbp), %r11
     addq   %r10, %r11
     movq   %r11, -112(%rbp)

     # generating code for i = #_t9
     movq   -112(%rbp), %r10
     movq   %r10, -72(%rbp)

     # generating code for goto FOR_COND_L9
     jmp    read_FOR_COND_L9

read_END_FOR_L10:
     # generating code for #_t10 = pgm_close()
     movq   $0, %rax
     call   pgm_close
     movq   %rax, -120(%rbp)

     leave
     .cfi_def_cfa 7, 8
     ret
     .cfi_endproc
