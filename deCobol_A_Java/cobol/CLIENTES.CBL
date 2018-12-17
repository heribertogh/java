      *
      *  PROGRAMA DE MANTENIMINETO DE CLIENTES
      *
       Identification division.
       Program-id. clientes.
       Environment division.
       Configuration section.
       Source-computer.  rmc85.
       Object-computer.  rmc85.
       Special-names.
           decimal-point is comma.

       Input-output section.
       File-control.

           select  clientes     assign to "clientes.inx"
                                organization is indexed
                                access mode is dynamic
                                record key is cl-nif
                                alternate record key is cl-nombre
                                alternate record key is cl-poblacion with duplicates.

       Data division.
       File section.

       Fd  clientes             label record is standard.
       01  r-clientes.
           10  cl-nif           pic x(09).
           10  cl-nombre        pic x(45).
           10  cl-domicilio     pic x(45).
           10  cl-poblacion     pic x(45).
           10  cl-postal        pic 9(05).
           10  cl-fecha-alta    pic 9(08).
           10  cl-fac-mes       pic 9(09)v99.
           10  cl-fac-ano       pic 9(09)v99.
           10  cl-kilometro     pic 9(03)v99.
           10  cl-nota          pic 9(05)v99.

       Working-storage section.

       01  sw  pic 9(01).
 
       copy  "clie.ws".
       copy  "msg.ws".
       copy  "rmpanels.ws".

       Procedure division.
       Programa section.
       Inicio.
           open  i-o                        clientes.
           go to                            display-pantalla.

           copy  "clie.prc".
           copy  "msg.prc".


       Display-pantalla.
           perform                          rmp--da-clie.
           perform                          rmp--iaf-clie.
           move "P-NIF" to rmp--field-name  perform  rmp--efi-clie.
           move "B-ESC" to rmp--field-name  perform  rmp--efi-clie.
           set  rmp--show-fields            to  true.    
           perform                          rmp--dp-clie.

           set  rmp--first-field            to  true.
           perform  with test after  until  b-esc  or  enter-key
             perform                        rmp--ee-clie
           end-perform.

           if  b-esc                        go to  fin-programa.

       Read-clientes.
           move  p-nif                      to  cl-nif.
           move  1                          to  sw.
           read  clientes  invalid key      go to  accept-datos.

           move  0                          to  sw.
           move  cl-nombre                  to  p-nom.
           move  cl-domicilio               to  p-dom.
           move  cl-poblacion               to  p-pob.
           move  cl-postal                  to  p-cpos.
           move  cl-fecha-alta              to  p-fal.
           move  cl-fac-mes                 to  p-fmes.
           move  cl-fac-ano                 to  p-fano.
           move  cl-kilometro               to  p-km.
           move  cl-nota                    to  p-nota.

       Accept-datos.
           perform                          rmp--ea-clie.
           move "P-FEC" to rmp--field-name  perform  rmp--dfi-clie.
           move "P-NIF" to rmp--field-name  perform  rmp--dfi-clie.
           if  sw = 1
           move "B-BOR" to rmp--field-name  perform  rmp--dfi-clie.
           perform                          rmp--daf-clie.

           perform  with test after        until b-gra or b-bor or b-esc
             perform                        rmp--ee-clie 
           end-perform.

           if  b-bor  
             move  "¨ BORRAR REGISTRO (S/N) ?"  to  v-msg
             perform                        pedir-respuesta
             if  v-res = "N"                go to  accept-datos   end-if
             delete  clientes   invalid key  continue  end-delete.

           if  b-gra
             move  "¨ GRABAR REGISTRO (S/N) ?"  to  v-msg
             perform                        pedir-respuesta
             if  v-res = "N"                go to  accept-datos   end-if
             move  p-nom                    to  cl-nombre    
             move  p-dom                    to  cl-domicilio 
             move  p-pob                    to  cl-poblacion 
             move  p-cpos                   to  cl-postal    
             move  p-fal                    to  cl-fecha-alta
             move  p-fmes                   to  cl-fac-mes   
             move  p-fano                   to  cl-fac-ano   
             move  p-km                     to  cl-kilometro
             move  p-nota                   to  cl-nota
             write  r-clientes  invalid key  rewrite  r-clientes.

           go to                            display-pantalla.

       Pedir-respuesta.
           set  rmp--show-fields            to  true.
           set  rmp--first-field            to  true.
           move  spaces                     to  v-res.
           perform                          rmp--dp-msg.
           perform                          rmp--ee-msg.
           if  not (v-res = "S"  or = "N")  go to  pedir-respuesta.
           perform                          rmp--rp-msg.


       Fin-programa.
           perform                          rmp--rp-clie.
           close                            clientes.
           exit program.
       Stop-programa.
           stop run.
