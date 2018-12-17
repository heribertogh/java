      *
      *  PROGRAMA DE MANTENIMINETO DE FACTURAS
      *
       Identification division.
       Program-id. gestion.
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
                                alternate record key is fa-nif with duplicates.

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
       copy  "gest.ws".
       copy  "erro.ws".
       copy  "msg.ws".

       01  w-numero.
           10  w-aaa           pic 9(04).
           10  w-num           pic 9(04). 
       01  ele                  pic  9(02).


       01  lin-00.
           10  inicio-impresora      pic xx     value "E".
           10  margen-superior       pic xxxxx  value "&l1E".
           10  margen-superior       pic xxxxx  value "&l1H".
           10  filler    pic x(44) value 
               "*o1D&l1H&l3a0L&l0o0E*t300R*r-3U*o0Q".
           10  reinicializar-laser.
             15  long-texto          pic xxxxxxx value "&l200F".
             15  entrelineado        pic xxxxx  value "&l4D".
             15  fuente-primaria     pic xxxx   value "(3@".
             15  tabla-catact-437    pic xxxxx  value "(10U".
             15  borrar-margenes-hor pic xx     value "9".
             15  peso-del-trazo-med  pic xxxxx  value "(s0B".
             15  unidad-de-medida    pic x(07)  value "&u600D".
             15  font-times          pic x(20)  value 
                                       "(s4099t0b0s10.00v1P".

       01  lin-01.
           10  lno pic x(21) value "(s16901t0b0s10.00v1P".
           10  xx        pic x(08) value "*p1200X".
           10  l1-cli    pic x(45).
       01  lin-02.
           10  xx        pic x(07) value "*p200X".
           10  l1-fac    pic z.zz9.
           10  filler    pic x(01)  value "/".
           10  l1-ano    pic xxxx.
           10  xx        pic x(07) value "*p650X".
           10  l1-hoj    pic 99 value 01.
           10  xx        pic x(08) value "*p1200X".
           10  l2-dom    pic x(45).
       01  lin-03.
           10  xx        pic x(08) value "*p1200X".
           10  l3-pob    pic x(52).
       01  lin-04.
           10  xx        pic x(07) value "*p200X".
           10  l4-fec    pic 99/99/9999.
           10  xx        pic x(07) value "*p620X".
           10  l4-nif    pic x(09).
       01  lin-41.
           10  xx        pic x(08) value "*p1200X".
           10  l4-pro    pic x(45).
           10  f-times   pic x(19)  value "(s4099t0b0s9.50v1P".
           10  entrelin  pic xxxxx  value "&l8D".
       01  lin-44.
           10  filler  pic x(01) value SPACES.
       01  lin-55.
           10  filler  pic x(1) value SPACES.
       01  lin-05.
           10  xx        pic x(06) value "*p60X".
           10  l5-can    pic zzz.zzz.
           10  l5-com    pic x.
           10  l5-dec    pic 99  blank when zero.
           10  xx        pic x(07) value "*p230X".
           10  l5-con    pic x(50).
           10  xx        pic x(08) value "*p1810X".
           10  l5-pre    pic zzzz.zz9,99 blank when zero.
           10  xx        pic x(08) value "*p2040X".
           10  l5-imp    pic zzz.zzz.zz9,99 blank when zero.
       01  lin-06.
           10  entrelin  pic xxxxx  value "&l8D".
           10  lno pic x(21) value "(s16901t0b0s10.00v1P".
           10  xx        pic x(08) value "*p2040X".
           10  l6-sum    pic zzz.zzz.zz9,99.
       01  lin-07.
           10  xx        pic x(08) value "*p1900X".
           10  l7-tiva   pic z9.
           10  xx        pic x(08) value "*p2040X".
           10  l7-iva    pic zzz.zzz.zz9,99.
       01  lin-08.
***********10  xx        pic x(08) value "*y1600X".
           10  xx        pic x(08) value "*p2040X".
           10  l8-tot    pic zzz.zzz.zz9,99.
       01  lin-d1.
           10  entrelin  pic xxxxx  value "&l8D".
           10  lno pic x(21) value "(s16901t0b0s10.00v1P".
           10  xx        pic x(08) value "*p1500X".
           10  filler    pic x(14)  value "SUMA.........:".
           10  xx        pic x(08) value "*p2040X".
           10  d1-sum    pic zzz.zzz.zz9,99.
       01  lin-d2.
           10  xx        pic x(08) value "*p1500X".
           10  filler    pic x(10)  value "DESCUENTO ".
           10  d2-tde    pic 9,9.
           10  filler    pic x(02)  value "%:".
           10  xx        pic x(08) value "*p2040X".
           10  d2-des    pic zzz.zzz.zz9,99.
           

       01  w-sump        pic 9(09)v99.
       01  w-suma        pic 9(09)v99.
       01  w-des         pic 9(09)v99.
       01  w-iva         pic 9(09)v99.
       01  w-imp         pic 9(09)v99.
       01  w-fecha.
           10  w-ano     pic 9(04).
           10  w-mes     pic 9(02).
           10  w-dia     pic 9(02).

       Procedure division.
       Programa section.
       Inicio.
           open  input                      clientes.
           open  i-o                        facturas.


           move  high-values                to  fa-numero.
           start  facturas  key  not > fa-numero
             invalid key                    move  1  to  fa-numero 
             not invalid key 
               read  facturas  next at end  move  1  to  fa-numero
                 not at end                 add   1  to  fa-numero
               end-read
           end-start.

           go to                            display-pantalla.

           copy  "gest.prc".
           copy  "erro.prc".
           copy  "msg.prc".


       Display-pantalla.
           perform                          rmp--da-gest.
           perform                          rmp--iaf-gest.
           move "F-NUM" to rmp--field-name  perform  rmp--efi-gest.
           move "F-ANO" to rmp--field-name  perform  rmp--efi-gest.
           move "B-ESC" to rmp--field-name  perform  rmp--efi-gest.


           move  fa-numero                  to  w-numero.
           move  w-num                      to  f-num.
           move  w-aaa                      to  f-ano.
           set  rmp--show-fields            to  true.    
           perform                          rmp--dp-gest.


       Accept-clave.
           set  rmp--first-field            to  true.
           perform  with test after  until  b-esc  or
                          ( enter-key  AND  RMP--LAST-FIELD = "F-NUM")
             perform                        rmp--ee-gest

*************if  f-num > w-num  and  not  b-esc
************** move  "NUMERO DE FACTURA NO GUARDA SECUENCIA" to v-err 
************** perform                      ver-error
***************move  w-num                  to  f-num
***************move  0                      to  rmp--exception-number
*************end-if

           end-perform.


           if  b-esc                        go to  fin-programa.


       Read-facturas.
           move  16                         to  f-tiva. 
           move  f-num                      to  w-num.
           move  f-ano                      to  w-aaa.
           move  w-numero                   to  fa-numero.
           read  facturas  invalid key 
             move  "NUMERO DE FACTURA NO GRABADO" to v-err 
             perform                        ver-error
             go to                          accept-clave.

           if  fa-situacion = "P"
             move  0                        to  f-tiva.
             

           move  fa-nif                     to  f-nif.
           move  fa-fecha                   to  f-fec.
           move  fa-situacion               to  f-sit.
           move  fa-cobrado                 to  f-cob.
           move  fa-descuento               to  f-tdes.

           move  0                          to  ele  f-tot.
           perform                          14  times
             add   1                        to  ele
             compute  f-tot  rounded = f-tot + 
                                     (fa-cantidad(ele) * fa-precio(ele))
           end-perform.
           compute  f-des rounded =         ( f-tot * f-tdes / 100 ).
           compute  f-tot rounded =         f-tot - f-des.
           compute  f-iva rounded =         f-tot * f-tiva / 100.
           compute  f-tot rounded =         f-iva + f-tot.
           compute  f-imp rounded =         f-tot - f-cob.

       Read-clientes.
           move  f-nif                      to  cl-nif.
           read  clientes
             invalid key                    move  spaces    to  p-nom 
                                            move  spaces  to  r-clientes
             not invalid key                move  cl-nombre to  p-nom.


       Accept-datos.
           perform                          rmp--da-gest.
           move "F-PAG" to rmp--field-name  perform  rmp--efi-gest.
           move "B-ESC" to rmp--field-name  perform  rmp--efi-gest.
           move "B-IMP" to rmp--field-name  perform  rmp--efi-gest.
           move "B-GRA" to rmp--field-name  perform  rmp--efi-gest.
           perform                          rmp--daf-gest.


           set  rmp--first-field            to  true.
           perform  with test after        until b-gra or b-imp or b-esc
             perform                        rmp--ee-gest 

             compute  f-cob =               fa-cobrado + f-pag
             compute  f-imp rounded =       f-tot - f-cob 
             move "F-COB" to rmp--field-name  perform  rmp--df-gest 
             move "F-IMP" to rmp--field-name  perform  rmp--df-gest 

           end-perform.

           if  b-imp  
             move  "¨ IMPRIMIR FACTURA (S/N) ?"  to  v-msg
             perform                        pedir-respuesta
             if  v-res = "N"                go to  accept-datos   end-if
             perform                        imprimir 
             if  fa-situacion = "S"
               move  "I"                    to  fa-situacion
               rewrite  r-facturas  invalid key  continue.


           if  b-gra
             if  fa-situacion = "S"
               move  "FACTURA SIN IMPRIMIR" to v-err 
               perform                      ver-error
               go to                        accept-datos          end-if

             if  f-cob > f-tot
               move  "TOTAL COBRADO MAYOR TOTAL FACTURA" to v-err 
               perform                      ver-error
               go to                        accept-datos          end-if

             if  f-cob < 0    
               move  "TOTAL COBRADO NEGATIVO" to v-err 
               perform                      ver-error
               go to                        accept-datos          end-if

             move  "¨ GRABAR DATOS (S/N) ?"  to  v-msg
             perform                        pedir-respuesta
             if  v-res = "N"                go to  accept-datos   end-if
             compute  fa-cobrado =          fa-cobrado + f-pag
             move  "I"                      to  fa-situacion
             if  fa-cobrado = f-tot    move "C" to fa-situacion   end-if
             rewrite  r-facturas  invalid key  continue.


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


       Fin-programa.
           perform                          rmp--rp-gest.
           close                            clientes.
           close                            facturas.
           exit program.

       Stop-programa.
           stop run.

       Imprimir.
           open  output                     impreso.

           move  fa-numero                  to  l1-fac.
           move  fa-numero                  to  l1-ano.
           move  cl-nombre                  to  l1-cli.
           move  cl-domicilio               to  l2-dom.
           move  spaces                     to  l3-pob.
           string cl-postal "  " cl-poblacion delimited by size
                                            into l3-pob.

           move  fa-fecha                   to  w-fecha 
           compute  l4-fec = w-dia * 1000000 + w-mes * 10000 + w-ano.
           move  fa-nif                     to  l4-nif.
           move  spaces                     to  l4-pro.
           compute  ele =                   cl-postal / 1000.

           if  ele > 0 and < 46
             if  ele = 28             move  "MADRID" to  l4-pro end-if
             if  ele = 45             move  "TOLEDO" to  l4-pro end-if
             if  ele = 14             move  "CASTELLON" to L4-pro end-if
             if  ele = 06             move  "BADAJOZ" to l4-pro end-if
             if  ele = 08             move  "BARCELONA" to l4-pro end-if
             if  ele = 25             move  "LLEIDA" to  l4-pro end-if
             if  ele = 50             move  "ZARAGOZA" to l4-pro end-if
             if  ele = 17             move  "GIRONA"  to l4-pro end-if
             if  ele = 48             move  "VIZCAYA" to l4-pro end-if
           end-if.


************ move   w-prov (ele)            to  l4-pro.

           write  linea                     from  lin-00  before 1.
           write  linea                     from  lin-01  after 10.
           write  linea                     from  lin-02  after 01.
           write  linea                     from  lin-03  after 01.
           write  linea                     from  lin-04  after 01.
           write  linea                     from  lin-41  after 01.
           write  linea                     from  spaces  after 09.
           write  linea                     from  lin-44  after 01.


           move  zeros                      to  ele  w-suma.
           perform                          14  times
             add  1                         to  ele 

             move  fa-cantidad (ele)        to  l5-can 
             move  fa-concepto (ele)        to  l5-con
             move  fa-precio   (ele)        to  l5-pre
             compute w-imp rounded = fa-cantidad (ele) * fa-precio (ele)
             move  w-imp                    to  l5-imp
             compute  w-suma rounded = w-suma + w-imp

             write  linea                   from  lin-05  after 02 
           end-perform.
             
      *01  lin-05.
      *    10  l5-com    pic x.
      *    10  l5-dec    pic 99  blank when zero.

           move  w-suma                     to  w-sump.
           compute  w-des rounded = w-suma * fa-descuento / 100.
           compute  w-suma rounded = w-suma - w-des.
           move  w-suma                     to  l6-sum
           move  f-tiva                     to  l7-tiva
           compute l7-iva rounded w-iva rounded = w-suma * f-tiva / 100.
           compute l8-tot rounded = w-suma + w-iva.


           write  linea                     from  lin-55  after 02.
           if  fa-descuento = 0
             write  linea                     from  spaces  after 07 
             write  linea  from entrelin of lin-06  after 01
           else
             move  w-sump                     to  d1-sum
             move  fa-descuento               to  d2-tde
             move  w-des                      to  d2-des
             write  linea                     from  spaces  after 05 
             write  linea                     from  lin-d1  after 01 
             write  linea                     from  lin-d2  after 02.

           write  linea                     from  lin-06  after 03.
           write  linea                     from  lin-07  after 02.
           write  linea                     from  lin-08  after 02.
           write  linea                     from  spaces  before page.

           close                            impreso.

