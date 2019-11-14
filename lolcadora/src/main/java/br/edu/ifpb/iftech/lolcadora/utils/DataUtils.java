package br.edu.ifpb.iftech.lolcadora.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {

    public static LocalDate adicionarDias(LocalDate data, int dias){
        LocalDate calendario = LocalDate.of(data.getYear(), data.getMonth(), data.getDayOfMonth());
        calendario.plusDays(dias);
        return calendario;
    }
    public static boolean isMaiorIdade(LocalDate dataNascimento){
        LocalDate dataAtual = LocalDate.now();
        Period anos = Period.between(dataNascimento, dataAtual);
        return anos.getYears() >= 18;
    }

    public static LocalDate obterDataFutura(int dias){
        return adicionarDias(LocalDate.now(), dias);
    }

    public static boolean isMesmaData(LocalDate dataInicio, LocalDate dataFim){
        Period datas = Period.between(dataFim, dataInicio);

        return datas.getDays() == 0;
    }

    public static boolean isMesmoDia(Date data, int diaSemana){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_WEEK) == diaSemana;
    }
}
