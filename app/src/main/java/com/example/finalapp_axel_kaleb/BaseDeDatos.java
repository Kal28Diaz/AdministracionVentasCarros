package com.example.finalapp_axel_kaleb;

public class BaseDeDatos {

    //Tabla "Vehiculo"
    public static final class Vehiculo {
        public static final String NOMBRE = "vehiculo";

        //Columnas de la tabla "Vehiculo"
        public static final class Columnas {
            public static String UUID = "vehiculoid";
            public static String MODELO = "modelo";
            public static String VERSION = "version";
            public static String TRANSMISION = "transmision";
            public static String ESTADO = "estado";
            public static String AÑO = "año";
        }
    }

    //Tabla "Cliente"
    public static final class Cliente {
        public static final String NOMBRE = "cliente";

        //Columnas de la tabla "Cliente"
        public static final class Columnas {
            public static String UUID = "clienteid";
            public static String NOMBRES = "nombre";
            public static String APELLIDO_PATERNO = "apellido_paterno";
            public static String APELLIDO_MATERNO = "apellido_materno";
            public static String CELULAR = "celular";
            public static String ACTIVIDAD_LABORAL = "actividad_laboral";
            public static String HISTORIAL_CREDITICIO = "historial_crediticio";
            public static String COMPROBANTE_INGRESOS = "comprobante_ingresos";
            public static String ENGANCHE = "enganche";
            public static String FECHA_CONTACTO = "fecha_contacto";
        }
    }

    //Tabla Tramite
    public static final class Tramite {
        public static final String NOMBRE = "tramite";
        //Columnas de la tabla "Tramite"
        public static final class Columnas{
            public static String UUID = "tramiteid";
            public static String ACTIVIDAD = "actividad";
            public static String PERFIL_VEHICULO = "perfil_vehiculo";
            public static String ETAPA = "etapa";
            public static String FINANCIAMIENTO = "financiamiento";
            public static String ID_CLIENTE = "clienteid";
            public static String ID_VEHICULO = "vehiculoid";
        }
    }

    //Tabla Nota
    public static final class Nota {
        public static final String NOMBRE = "nota";
        //Columnas de la tabla "Nota"
        public static final class Columnas{
            public static String UUID = "notaid";
            public static String TITULO = "titulo";
            public static String CUERPO = "cuerpo";
            public static String ID_TRAMITE = "tramiteid";
        }
    }

    //Tabla Cita
    public static final class Cita {
        public static final String NOMBRE = "cita";
        //Columnas de la tabla "Cita"
        public static final class Columnas{
            public static String UUID = "citaid";
            public static String TITULO = "titulo";
            public static String FECHA = "fecha";
            public static String HORA = "hora";
            public static String ID_TRAMITE = "tramiteid";
        }
    }

}
