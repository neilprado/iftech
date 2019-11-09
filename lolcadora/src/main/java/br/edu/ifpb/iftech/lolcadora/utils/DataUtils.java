package br.edu.ifpb.iftech.lolcadora.utils;

import java.util.Calendar;
import java.util.Date;

public class DataUtils {

    public static Date adicionarDias(Date data, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return calendar.getTime();
    }

    public static Date obterDataFutura(int dias){
        return adicionarDias(new Date(), dias);
    }

    public static boolean isMesmaData(Date dataInicio, Date dataFim){
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(dataInicio);

        Calendar fim = Calendar.getInstance();
        fim.setTime(dataFim);

        return (inicio.get(Calendar.DAY_OF_MONTH) == fim.get(Calendar.DAY_OF_MONTH))
                && inicio.get(Calendar.MONTH) == fim.get(Calendar.MONTH)
                && inicio.get(Calendar.YEAR) == fim.get(Calendar.YEAR);
    }

    public static boolean isMesmoDia(Date data, int diaSemana){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_WEEK) == diaSemana;
    }
}
