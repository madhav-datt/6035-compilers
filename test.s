     .comm  image, 1040000, 64
     .comm  size, 8, 8
     .comm  rows, 8, 8
     .comm  cols, 8, 8
     .globl main

.STR_0:
     .string "saman_sl.pgm"
.STR_1:
     .string "saman.pgm"
     
main:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*2), %rsp
     movq   %r12, -8(%rbp)
     # generating code for #_t0 = read()
     movq   $0, %rax
     call   read
     movq   %rax, %r12
     # generating code for #_t1 = start_caliper()
     movq   $0, %rax
     call   start_caliper
     movq   %rax, %r12
     # generating code for #_t2 = shift_left()
     movq   $0, %rax
     call   shift_left
     movq   %rax, %r12
     # generating code for #_t3 = end_caliper()
     movq   $0, %rax
     call   end_caliper
     movq   %rax, %r12
     # generating code for #_t4 = write()
     movq   $0, %rax
     call   write
     movq   %rax, %r12
     movq   -8(%rbp), %r12
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

     
shift_left:
      .cfi_startproc  
     pushq  %rbp
     .cfi_def_cfa_offset 16
     .cfi_offset 6, -16
     movq   %rsp, %rbp

     subq   $(8*9), %rsp
     movq   %rbx, -8(%rbp)
     movq   %r13, -16(%rbp)
     movq   %r15, -24(%rbp)
     movq   %r12, -32(%rbp)
     movq   %r14, -40(%rbp)
     # generating code for #_t0 = 0
     # generating code for 0

     movq   $0, %r12

     # generating code for i = #_t0
     movq   %r12, %r14

shift_left_FOR_COND_L2:
     # generating code for #_t1 = i < rows
     movq   rows(%rip), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r12

     # generating code for if #_t1 goto FOR_L3
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    shift_left_FOR_L3

     # generating code for goto END_FOR_L3
     jmp    shift_left_END_FOR_L3

shift_left_FOR_L3:
     # generating code for #_t2 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for j = #_t2
     movq   %r13, %r12

shift_left_FOR_COND_L9:
     # generating code for #_t3 = 30
     # generating code for 30

     movq   $30, %r13

     # generating code for #_t4 = cols - #_t3
     movq   %r13, %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r15

     # generating code for #_t5 = j < #_t4
     movq   %r15, %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t5 goto FOR_L10
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    shift_left_FOR_L10

     # generating code for goto END_FOR_L10
     jmp    shift_left_END_FOR_L10

shift_left_FOR_L10:
     # generating code for #_t6 = 303
     # generating code for 303

     movq   $303, %r15

     # generating code for #_t7 = i * #_t6
     movq   %r15, %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r13

     # generating code for #_t8 = #_t7 + j
     movq   %r12, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r15

     # generating code for #_t9 = 30
     # generating code for 30

     movq   $30, %r13

     # generating code for #_t10 = #_t8 + #_t9
     movq   %r13, %r10
     movq   %r15, %r11
     addq   %r10, %r11
     movq   %r11, %rbx

     # generating code for #_t11 = 303
     # generating code for 303

     movq   $303, %r15

     # generating code for #_t12 = i * #_t11
     movq   %r15, %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r13

     # generating code for #_t13 = #_t12 + j
     movq   %r12, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r15

     # generating code for image[#_t13]  = image[#_t10] 
     # array generating code for image[#_t10] 
     movq   %rbx, %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -48(%rbp)
     # array generating code for image[#_t13] 
     movq   %r15, %r10
     # adding something to -56(%rbp)
     movq   -48(%rbp), %r11
     movq   %r11, image(, %r10, 8)

     # generating code for #_t14 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t15 = j + #_t14
     movq   %r15, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t15
     movq   %r13, %r12

     # generating code for goto FOR_COND_L9
     jmp    shift_left_FOR_COND_L9

shift_left_END_FOR_L10:
     # generating code for #_t16 = 1
     # generating code for 1

     movq   $1, %r13

     # generating code for #_t17 = i + #_t16
     movq   %r13, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for i = #_t17
     movq   %r12, %r14

     # generating code for goto FOR_COND_L2
     jmp    shift_left_FOR_COND_L2

shift_left_END_FOR_L3:
     # generating code for #_t18 = 0
     # generating code for 0

     movq   $0, %r12

     # generating code for i = #_t18
     movq   %r12, %r14

shift_left_FOR_COND_L35:
     # generating code for #_t19 = i < rows
     movq   rows(%rip), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r12

     # generating code for if #_t19 goto FOR_L36
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    shift_left_FOR_L36

     # generating code for goto END_FOR_L36
     jmp    shift_left_END_FOR_L36

shift_left_FOR_L36:
     # generating code for #_t20 = 30
     # generating code for 30

     movq   $30, %r12

     # generating code for #_t21 = cols - #_t20
     movq   %r12, %r10
     movq   cols(%rip), %r11
     subq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t21
     movq   %r13, %r12

shift_left_FOR_COND_L43:
     # generating code for #_t22 = j < cols
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t22 goto FOR_L44
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    shift_left_FOR_L44

     # generating code for goto END_FOR_L44
     jmp    shift_left_END_FOR_L44

shift_left_FOR_L44:
     # generating code for #_t23 = 0
     # generating code for 0

     movq   $0, %r15

     # generating code for #_t24 = 303
     # generating code for 303

     movq   $303, %rbx

     # generating code for #_t25 = i * #_t24
     movq   %rbx, %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r13

     # generating code for #_t26 = #_t25 + j
     movq   %r12, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %rbx

     # generating code for image[#_t26]  = #_t23
     # array generating code for image[#_t26] 
     movq   %rbx, %r10
     # adding something to -64(%rbp)
     movq   %r15, %r11
     movq   %r11, image(, %r10, 8)

     # generating code for #_t27 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t28 = j + #_t27
     movq   %r15, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t28
     movq   %r13, %r12

     # generating code for goto FOR_COND_L43
     jmp    shift_left_FOR_COND_L43

shift_left_END_FOR_L44:
     # generating code for #_t29 = 1
     # generating code for 1

     movq   $1, %r13

     # generating code for #_t30 = i + #_t29
     movq   %r13, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for i = #_t30
     movq   %r12, %r14

     # generating code for goto FOR_COND_L35
     jmp    shift_left_FOR_COND_L35

shift_left_END_FOR_L36:
     movq   -8(%rbp), %rbx
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %r15
     movq   -32(%rbp), %r12
     movq   -40(%rbp), %r14
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

     subq   $(8*7), %rsp
     movq   %r13, -8(%rbp)
     movq   %r12, -16(%rbp)
     movq   %r15, -24(%rbp)
     movq   %r14, -32(%rbp)
     movq   %rbx, -40(%rbp)
     # generating code for #_t1 = pgm_open_for_write(#str_t0,cols,rows,)
     movq   $.STR_0, %rdi
     movq   cols(%rip), %rsi
     movq   rows(%rip), %rdx
     movq   $0, %rax
     call   pgm_open_for_write
     movq   %rax, %r12
     # generating code for #_t2 = cols * rows
     movq   rows(%rip), %r10
     movq   cols(%rip), %r11
     imulq  %r10, %r11
     movq   %r11, %r12

     # generating code for size = #_t2
     movq   %r12, %r13
     movq   %r12, %r10
     movq   %r10, size(%rip)

     # generating code for #_t3 = 0
     # generating code for 0

     movq   $0, %r12

     # generating code for i = #_t3
     movq   %r12, %r14

write_FOR_COND_L5:
     # generating code for #_t4 = i < rows
     movq   rows(%rip), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r12

     # generating code for if #_t4 goto FOR_L6
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    write_FOR_L6

     # generating code for goto END_FOR_L6
     jmp    write_END_FOR_L6

write_FOR_L6:
     # generating code for #_t5 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for j = #_t5
     movq   %r13, %r12

write_FOR_COND_L12:
     # generating code for #_t6 = j < cols
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t6 goto FOR_L13
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    write_FOR_L13

     # generating code for goto END_FOR_L13
     jmp    write_END_FOR_L13

write_FOR_L13:
     # generating code for #_t7 = 303
     # generating code for 303

     movq   $303, %r15

     # generating code for #_t8 = i * #_t7
     movq   %r15, %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r13

     # generating code for #_t9 = #_t8 + j
     movq   %r12, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %r15

     # generating code for #_t10 = pgm_write_next_pixel(image[#_t9] ,)
     # array generating code for image[#_t9] 
     movq   %r15, %rax
     movq   image( ,%rax, 8), %rax
     movq   %rax, -48(%rbp)
     movq   -48(%rbp), %r10
     movq   %r10, %rdi
     movq   $0, %rax
     call   pgm_write_next_pixel
     movq   %rax, %r13
     # generating code for #_t11 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t12 = j + #_t11
     movq   %r15, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t12
     movq   %r13, %r12

     # generating code for goto FOR_COND_L12
     jmp    write_FOR_COND_L12

write_END_FOR_L13:
     # generating code for #_t13 = 1
     # generating code for 1

     movq   $1, %r13

     # generating code for #_t14 = i + #_t13
     movq   %r13, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for i = #_t14
     movq   %r12, %r14

     # generating code for goto FOR_COND_L5
     jmp    write_FOR_COND_L5

write_END_FOR_L6:
     # generating code for #_t15 = pgm_close()
     movq   $0, %rax
     call   pgm_close
     movq   %rax, %r12
     movq   -8(%rbp), %r13
     movq   -16(%rbp), %r12
     movq   -24(%rbp), %r15
     movq   -32(%rbp), %r14
     movq   -40(%rbp), %rbx
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

     subq   $(8*7), %rsp
     movq   %r15, -8(%rbp)
     movq   %r13, -16(%rbp)
     movq   %rbx, -24(%rbp)
     movq   %r12, -32(%rbp)
     movq   %r14, -40(%rbp)
     # generating code for #_t1 = pgm_open_for_read(#str_t0,)
     movq   $.STR_1, %rdi
     movq   $0, %rax
     call   pgm_open_for_read
     movq   %rax, %r12
     # generating code for #_t2 = pgm_get_cols()
     movq   $0, %rax
     call   pgm_get_cols
     movq   %rax, %r12
     # generating code for cols = #_t2
     movq   %r12, %r15
     movq   %r12, %r10
     movq   %r10, cols(%rip)

     # generating code for #_t3 = pgm_get_rows()
     movq   $0, %rax
     call   pgm_get_rows
     movq   %rax, %r12
     # generating code for rows = #_t3
     movq   %r12, %r13
     movq   %r12, %r10
     movq   %r10, rows(%rip)

     # generating code for #_t4 = cols * rows
     movq   rows(%rip), %r10
     movq   cols(%rip), %r11
     imulq  %r10, %r11
     movq   %r11, %r12

     # generating code for size = #_t4
     movq   %r12, %r14
     movq   %r12, %r10
     movq   %r10, size(%rip)

     # generating code for #_t5 = 0
     # generating code for 0

     movq   $0, %r12

     # generating code for i = #_t5
     movq   %r12, %r14

read_FOR_COND_L9:
     # generating code for #_t6 = i < rows
     movq   rows(%rip), %r10
     movq   %r14, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r12

     # generating code for if #_t6 goto FOR_L10
     movq   %r12, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    read_FOR_L10

     # generating code for goto END_FOR_L10
     jmp    read_END_FOR_L10

read_FOR_L10:
     # generating code for #_t7 = 0
     # generating code for 0

     movq   $0, %r13

     # generating code for j = #_t7
     movq   %r13, %r12

read_FOR_COND_L16:
     # generating code for #_t8 = j < cols
     movq   cols(%rip), %r10
     movq   %r12, %r11
     cmp    %r10, %r11
     movq   $0, %r11
     movq   $1, %r10
     cmovl  %r10, %r11
     movq   %r11, %r13

     # generating code for if #_t8 goto FOR_L17
     movq   %r13, %r10
     movq   $0, %r11
     cmp    %r10, %r11
     jne    read_FOR_L17

     # generating code for goto END_FOR_L17
     jmp    read_END_FOR_L17

read_FOR_L17:
     # generating code for #_t9 = pgm_get_next_pixel()
     movq   $0, %rax
     call   pgm_get_next_pixel
     movq   %rax, %r15
     # generating code for #_t10 = 303
     # generating code for 303

     movq   $303, %rbx

     # generating code for #_t11 = i * #_t10
     movq   %rbx, %r10
     movq   %r14, %r11
     imulq  %r10, %r11
     movq   %r11, %r13

     # generating code for #_t12 = #_t11 + j
     movq   %r12, %r10
     movq   %r13, %r11
     addq   %r10, %r11
     movq   %r11, %rbx

     # generating code for image[#_t12]  = #_t9
     # array generating code for image[#_t12] 
     movq   %rbx, %r10
     # adding something to -48(%rbp)
     movq   %r15, %r11
     movq   %r11, image(, %r10, 8)

     # generating code for #_t13 = 1
     # generating code for 1

     movq   $1, %r15

     # generating code for #_t14 = j + #_t13
     movq   %r15, %r10
     movq   %r12, %r11
     addq   %r10, %r11
     movq   %r11, %r13

     # generating code for j = #_t14
     movq   %r13, %r12

     # generating code for goto FOR_COND_L16
     jmp    read_FOR_COND_L16

read_END_FOR_L17:
     # generating code for #_t15 = 1
     # generating code for 1

     movq   $1, %r13

     # generating code for #_t16 = i + #_t15
     movq   %r13, %r10
     movq   %r14, %r11
     addq   %r10, %r11
     movq   %r11, %r12

     # generating code for i = #_t16
     movq   %r12, %r14

     # generating code for goto FOR_COND_L9
     jmp    read_FOR_COND_L9

read_END_FOR_L10:
     # generating code for #_t17 = pgm_close()
     movq   $0, %rax
     call   pgm_close
     movq   %rax, %r12
     movq   -8(%rbp), %r15
     movq   -16(%rbp), %r13
     movq   -24(%rbp), %rbx
     movq   -32(%rbp), %r12
     movq   -40(%rbp), %r14
     leave   
     .cfi_def_cfa 7, 8
     ret     
     .cfi_endproc  

