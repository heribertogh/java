      *
      *  PROGRAMA DE MANTENIMINETO DE FACTURAS
      *
       Identification division.
       Program-id. facturas.
       Environment division.
       Configuration section.
       Source-computer.  rmc85.
       Object-computer.  rmc85.
       Special-names.
           decimal-point is comma.

       Input-output section.
       File-control.
     
           select  facturas     assign to "facturas.inx"
                                organization is indexed
                                access mode is dynamic
                                record key is fa-numero
                                alternate record key is fa-nif.

           select  clientes     assign to "clientes.inx"
                                organization is indexed
                                access mode is dynamic
                                record key is cl-nif.

       Data division.
       File section.

       Fd  facturas             label  record is standard.
       01  r-facturas.
             10  fa-nif         pic x(09).
             10  fa-numero      pic 9(08).
             10  fa-cantidad    pic 9(06).
             10  fa-concepto    pic x(50).
             10  fa-precio      pic 9(09).
             10  fa-fecha       pic 9(08).
             10  fa-situacion   pic x(01).
             10  fa-cobrado     pic 9(09).
             10  fa-descuento   pic 9(01).   


       Fd  clientes             label record is standard.
       01  r-clientes.
           10  cl-nif           pic x(09).
           10  cl-nombre        pic x(45).
           10  cl-domicilio     pic x(45).
           10  cl-poblacion     pic x(45).
           10  cl-postal        pic 9(05).
           10  cl-fecha-alta    pic 9(08).
           10  cl-fac-mes       pic 9(09).
           10  cl-fac-ano       pic 9(09).
           10  cl-km            pic 9(03).
           10  cl-nota          pic 9(05).

       Working-storage section.

       copy  "rmpanels.ws".
       copy  "fact.ws".
       copy  "erro.ws".
       copy  "msg.ws".

       01  sw                  pic 9(03).
       01  w-ant               pic 9(09)v99.
       01  ele                 pic 9(03).
       01  w-numero.
           10  w-ano           pic 9(04).
           10  w-num           pic 9(04).

       Procedure division.
       Programa section.
       Inicio.
           open  i-o                        clientes.
           open  i-o                        facturas.
           go to                            display-pantalla.

           copy  "fact.prc".
           copy  "erro.prc".
           copy  "msg.prc".


       Display-pantalla.
           perform                          rmp--da-fact.
           perform                          rmp--iaf-fact.
           move "F-ANO" to rmp--field-name  perform  rmp--efi-fact.
           move "F-NUM" to rmp--field-name  perform  rmp--efi-fact.
           move "B-ESC" to rmp--field-name  perform  rmp--efi-fact.

           move  high-values                to  fa-numero.
           start  facturas  key  not > fa-numero
             invalid key                    move  20010001 to fa-numero 
             not invalid key 
               read  facturas  next at end  move  20010001 to fa-numero
                 not at end                 add   1  to  fa-numero
               end-read
           end-start.

           move  fa-numero                  to  w-numero
           move  w-ano                      to  f-ano.
           move  w-num                      to  f-num.
           set  rmp--show-fields            to  true.    
           perform                          rmp--dp-fact.


           set  rmp--first-field            to  true.
           perform  with test after  until  b-esc  or
                           (enter-key  and  rmp--lasT-field = "F-NUM" )
             perform                        rmp--ee-fact

             if  not  b-esc
               if f-num > w-num  and  f-ano > w-ano
                 move  "NUMERO DE FACTURA NO GUARDA SECUENCIA" to v-err 
                 perform                      ver-error
                 move  w-num                  to  f-num
                 move  0                      to  rmp--exception-number
               end-if
             end-if

           end-perform.


           if  b-esc                        go to  fin-programa.


       Read-facturas.
           move  f-num                      to  w-num.
           move  f-ano                      to  w-ano
           move  w-numero                   to  fa-numero.
           move  1                          to  sw.
           read  facturas  invalid key      go to  read-clientes.

           move  0                          to  sw.
           move  fa-nif                     to  f-nif.
           move  fa-fecha                   to  f-fec.
           move  fa-situacion               to  f-sit.
           move  fa-cobrado                 to  f-cob.
           move  fa-descuento               to  f-des.

           move  0                          to  ele.
           perform                          14  times
             add   1                        to  ele
             move  fa-cantidad (ele)        to  f-can (ele)
             move  fa-concepto (ele)        to  f-con (ele)
             move  fa-precio   (ele)        to  f-pre (ele)
           end-perform.

           perform                          calcular-totales.
           move  f-tot                      to  w-ant.


       Calcular-totales.
           move  0                          to  ele  f-tot.
           perform                          14  times
             add   1                        to  ele
             compute  f-tot  rounded = f-tot + (f-can(ele) * f-pre(ele))
           end-perform.
           compute  f-tot rounded =       f-tot - ( f-tot * f-des / 100)
           compute  f-iva rounded =         f-tot * f-tiva / 100.
           compute  f-tot rounded =         f-iva + f-tot.
           move "F-IVA" to rmp--field-name  perform  rmp--df-fact.
           move "F-TOT" to rmp--field-name  perform  rmp--df-fact.
           move "F-PRE" to rmp--field-name  perform  rmp--df-fact.


       Read-clientes.
           move  f-nif                      to  cl-nif.
           read  clientes
             invalid key
               move  spaces                 to  p-nom 
               move  zeros                  to  cl-km  cl-nota
             not invalid key                move  cl-nombre to  p-nom.
           move  "P-NOM" to rmp--field-name  perform  rmp--df-fact.


       Accept-datos.
           perform                          rmp--da-fact.
           move "F-NIF" to rmp--field-name  perform  rmp--efi-fact.
           move "F-DES" to rmp--field-name  perform  rmp--efi-fact.
           move "F-FEC" to rmp--field-name  perform  rmp--efi-fact.
           move "F-SIT" to rmp--field-name  perform  rmp--efi-fact.
           move "F-COB" to rmp--field-name  perform  rmp--efi-fact.
           move "F-CAN" to rmp--field-name  perform  rmp--efi-fact.
           move "F-CON" to rmp--field-name  perform  rmp--efi-fact.
           move "F-PRE" to rmp--field-name  perform  rmp--efi-fact.
           move "B-GRA" to rmp--field-name  perform  rmp--efi-fact.
           move "B-BOR" to rmp--field-name  perform  rmp--efi-fact.
           move "B-ESC" to rmp--field-name  perform  rmp--efi-fact.
           move "B-KM"  to rmp--field-name  perform  rmp--efi-fact.
           move "B-NOTA" to rmp--field-name perform  rmp--efi-fact.
           if  sw = 1
           move "B-BOR" to rmp--field-name  perform  rmp--dfi-fact.
           perform                          rmp--daf-fact.

           set  rmp--first-field            to  true.
           perform  with test after        until b-gra or b-bor or b-esc
             perform                        rmp--ee-fact 

             if  not (b-esc  or  b-bor)  perform  read-clientes  end-if

             if  b-km  and 
                 ( rmp--last-field = "F-PRE" or = "F-CON" or = "F-CAN" )
               move  cl-km            to  f-pre(rmp--last-occur)  end-if
             if  b-nota  and
                 ( rmp--last-field = "F-PRE" or = "F-CON" or = "F-CAN" )
               move  cl-nota          to  f-pre(rmp--last-occur)  end-if

             perform                        calcular-totales 

             if (campo-pos or enter-key)  and  rmp--last-field = "B-ESC"
               move  "F-NIF"                to  rmp--next-field
             end-if
 
             if  campo-ant  and  rmp--last-field = "F-NIF"
               move  "B-ESC"                to  rmp--next-field
             end-if

             if  rmp--next-field = "B-KM" or = "B-NOTA"
               move  rmp--last-field        to  rmp--next-field
               move  rmp--last-occur        to  rmp--next-occur
             end-if

             if  ( campo-pos or enter-key ) and 
                   rmp--next-field  not = "B-GRA" 
               if  rmp--last-field = "F-CAN"
                 move  rmp--last-occur      to  rmp--next-occur
                 move  "F-CON"              to  rmp--next-field   end-if
               if  rmp--last-field = "F-CON"
                 move  rmp--last-occur      to  rmp--next-occur
                 move  "F-PRE"              to  rmp--next-field   end-if
               if  rmp--last-field = "F-PRE"
                 move  "F-CAN"              to  rmp--next-field   end-if
             end-if

             if  campo-ant  and  rmp--next-field  not = "F-COB"
               if  rmp--last-field = "F-PRE"
                 move  rmp--last-occur       to  rmp--next-occur
                 move  "F-CON"               to  rmp--next-field  end-if
               if  rmp--last-field = "F-CON"
                 move  rmp--last-occur       to  rmp--next-occur
                 move  "F-CAN"               to  rmp--next-field  end-if
               if  rmp--last-field = "F-CAN"
                 move  "F-PRE"               to  rmp--next-field  end-if
             end-if
           end-perform.

           if  b-bor  
             move  "¨ BORRAR FACTURA (S/N) ?"  to  v-msg
             perform                        pedir-respuesta
             if  v-res = "N"                go to  accept-datos   end-if
             delete  facturas   invalid key  continue  end-delete 
             move  0                        to  f-tot
             perform                        actualizar-clientes.

           if  b-gra
             if  p-nom = spaces            
               move  "CLIENTE NO ESTA DADO DE ALTA" to v-err 
               perform                      ver-error
               go to                        accept-datos
             end-if

             if  not  ( f-sit = "C" or = "I" or = "S" or = "P" )  or
                 ( f-cob < f-tot  and  f-sit = "C" )
               move  "SITUACION FACTURA NO ES VALIDA" to v-err 
               perform                      ver-error
               go to                        accept-datos
             end-if

             if  f-cob > f-tot
               move  "IMPORTE COBRADO MAYOR QUE TOTAL FACTURA" to v-err 
               perform                      ver-error
               go to                        accept-datos
             end-if

             move  "¨ GRABAR FACTURA (S/N) ?"  to  v-msg
             perform                        pedir-respuesta
             if  v-res = "N"                go to  accept-datos   end-if

             move  f-nif                    to  fa-nif 
             move  f-fec                    to  fa-fecha 
             move  f-sit                    to  fa-situacion 
             move  f-cob                    to  fa-cobrado 
             move  f-des                    to  fa-descuento
             move  0                        to  ele 
             perform                        14  times
               add   1                      to  ele
               move  f-can (ele)            to  fa-cantidad (ele)
               move  f-con (ele)            to  fa-concepto (ele)
               move  f-pre (ele)            to  fa-precio   (ele)
             end-perform             
             write  r-facturas invalid key rewrite  r-facturas end-write
             perform                        actualizar-clientes.

           go to                            display-pantalla.


       Pedir-respuesta.
           set  rmp--show-fields            to  true.
           set  rmp--first-field            to  true.
           move  spaces                     to  v-res.
           perform                          rmp--dp-msg.
           perform                          rmp--ee-msg.
           if  not (v-res = "S"  or = "N")  go to  pedir-respuesta.
           perform                          rmp--rp-msg.

       Ver-error.
           set  rmp--show-fields            to  true.
           set  rmp--first-field            to  true.
           perform                          rmp--dp-erro.
           perform                          rmp--ee-erro.
           perform                          rmp--rp-erro.

       Actualizar-clientes.
           move  fa-nif                     to  cl-nif.
           read  clientes  invalid key      continue
           not invalid key
             compute  cl-fac-mes = cl-fac-mes - w-ant + f-tot
             compute  cl-fac-ano = cl-fac-ano - w-ant + f-tot
             rewrite                        r-clientes.


       Fin-programa.
           perform                          rmp--rp-fact.
           close                            clientes.
           close                            facturas.
           exit program.

       Stop-programa.
           stop run.




