package Serie;

import Serie.Encript.Encripta;
import Serie.PlacaDES.PlacaES;
import Serie.Registry.Registry;
import Serie.Sockets.SimpleClient;
import Serie.Supervisor.CambioClaveSupervisor;
import Serie.Supervisor.IngreseSupervisor;
import Serie.Supervisor.Test;
import Serie.Supervisor.frmCierre;
import Serie.Supervisor.frmConsulta;
import Serie.Ventanas.FrameCancelar;
import Serie.Ventanas.FrameVideo;
import Serie.Ventanas.IngreseClave;
import Serie.Ventanas.TarjetaClave;
import Serie.Ventanas.frmDepositos;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

public class principal {
    
    private static final Logger log = Logger.getLogger(principal.class);
    
    @SuppressWarnings("UnusedAssignment")
    public static void main(String[] arg){
        DE50.respSer.clear();  
        Registry reg = new Registry();
        reg.llenarVariableReg();
//        DE50.template= reg.leerTemplate();
//        DE50.ipServer = reg.leerIp();
//        DE50.socketServ = reg.leerSocket();
//        DE50.tiempoTimer= reg.leerTimeout();
//        DE50.cancelTimeOut=reg.leerCancelTimeout();
//        DE50.tipoAcceso=reg.leerPuertaTesoro();
//        DE50.testDeposito=reg.leerTestDeposito();
//        DE50.tiempoAbrirEscrow=reg.leerAbrirEscrow();
//        DE50.tiempo=((Integer.parseInt(DE50.tiempoTimer))/1000);
//        DE50.tiempoCancel=((Integer.parseInt(DE50.cancelTimeOut))/1000);
        //Iguala la variable global de la clase EstadoPlaca al resultado de la consulta 
        //de la variable del registro, false siempre va a sensar el estado de la placa
        //true la va a ignorar y continuar, en caso de que una placa se dañe 
        //o bien para realizar pruebas
        BDdeposito BD = new BDdeposito();
//        String Precinto=BD.traerPrecinto();
//        System.out.println("Precinto"+Precinto);
        DE50.denomisP.clear();
        DE50.denomisD.clear();
        DE50.denomisE.clear();
        DE50.denomisP=BD.cambiarEtiquetasBilletes(1);//Denominaciones Pesos
        DE50.denomisD=BD.cambiarEtiquetasBilletes(2);//Denominaciones Dolares
        DE50.denomisE=BD.cambiarEtiquetasBilletes(3);//Denominaciones Euros
        File logs = new File("C:\\Logs");
        File respaldo = new File("C:\\Respaldo");
        
        if(!logs.exists()){
            logs.mkdirs();
        }
        if(!respaldo.exists()){
            respaldo.mkdirs();
        }
        PlacaES.ignorar = reg.leerIgnora();
        log.info("Placa DES ignorada: "+PlacaES.ignorar);
        try {
            InetAddress address = InetAddress.getLocalHost();
            DE50.ipLocal=address.toString();
            String sHostName;
            sHostName = address.getHostName();
            int l=DE50.ipLocal.length();
            int i=sHostName.length();
            i=i+1;
            DE50.ipLocal=DE50.ipLocal.substring(i,l);
        } catch (UnknownHostException ex) {
            log.error("Registry en principal "+ex);
        }
        frmCierre consulta = new frmCierre("", false);
////        System.out.println("creada");
//        Test frmTest = new Test();
//        frmTest.setVisible(true);
//        String track2="21214120060120333040;4062900001717006=";
//        String pin2="7BDB89A78CCD5658";
//        String cuenta2="CT00021000101003110032";
//        String moneda="0032";
//        String fe="";
//        String ho="";
//        Date a = new Date();
//        SimpleDateFormat sdt = new SimpleDateFormat("yyMMdd");
//        fe=sdt.format(a.getTime()); //Saco la fecha del sistema en formato yymmdd y la guardo como String
//        SimpleDateFormat sdh = new SimpleDateFormat("HHmmss");
//        ho=sdh.format(a.getTime());
//        System.out.println("hora "+ho);
//        FrameVideo FV = new FrameVideo(true);
//            IngreseSupervisor supe = new IngreseSupervisor(false);
//                    supe.setVisible(true);
//          int prueba= BD.consultaLimiteBilletes();
//          System.out.println("prueba "+prueba);
//          BD.depoNoTransmitido();
//        String saldoNuevo ="9122334583070408";
////        DecimalFormat dF = new DecimalFormat("###,###.##"); 
//        double saldoTemp = Double.parseDouble(saldoNuevo);
//        System.out.println("Saldo"+saldoNuevo);
//        saldoNuevo=String.format("%,.0f", saldoTemp);
//        System.out.println("Saldo operativo: "+ "" + saldoNuevo);
//          FrameCancelar FC = new FrameCancelar(true);
        
//            LeerTarjeta LT = new LeerTarjeta();
//            System.out.println(LT.leerTarjeta());

//          TarjetaClave IC = new TarjetaClave(true);
//        frmDepositos frmD = new frmDepositos(true);
//        frmD.otrosValores(track2, pin2, cuenta2, moneda);
//        Encripta enc = new Encripta();
//        String pin="";
//        for(int i=0; i<10000; i++){
//            pin=String.format("%04d", i);
//            enc.encripta("0000" + pin);
//            if(enc.encripta("0000" + pin).length()!=16){
//                System.out.println(pin);
//                System.out.println(enc.encripta("0000" + pin));
//            }
//            
//        }
            
        
    /*  //Para probar el lector de tarjetas solo
        String track;
        LeerTarjeta lt = new LeerTarjeta();
        track=lt.leerTarjeta();
        System.out.println(track);
        
    */    
        //Este es el Frame Principal del Programa//
        /*LLama al Frame que activa el Video para ser 
        reproducido continuamente hasta que la pantalla 
        reciba un evento Click
        ///////////////////////////////////////////////
        En este Frame se realiza:
        *El código para poder reproducir el video del banco
        *Se llama a una clase Sensado que permite verificar 
        la funcionalidad de los perifericos del equipo TASDE50
        *Se realiza la primera consulta de sistemas al Servidor 
        del banco
        *Se reconoce el estado de la Placa DES para saber si debe 
        acceder normal a la aplicaciòn o ingresar al MenúSupervisor
        *Atravès de un mètodo alternativo también se accede al MenúSupervisor
        tecleando la clave azor + ENTER
        ///////////////////////////////////////////////////////////
        En construcciòn acceso por teclado a un Menù Tècnico
        */
        ///////////////////////////////////////////////////////////
        //Destramado de la consulta de la variable sucursal de la consulta del Servidor.
        //Para incluirla en las impresiones de los tiques de Deposito y Cierre
   /*     String Mensaje="";
        String temporal="";
        int i=0;
        
        Mensaje=FV.peticionServidor();
        if(Mensaje.equals("Operacion Exitosa")){
            //// Traigo los valores de sucursal y los separo
            temporal=DE50.respSer.get(12);
            StringTokenizer stringT = new StringTokenizer(temporal,"\\");
            while (stringT.hasMoreTokens()) {  
                DE50.sucursal [i] = stringT.nextToken();  
                i++;
            }
        }*/
    }
   
}
