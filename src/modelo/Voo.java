package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Voo {
	
	public enum Status { CONFIRMADO, ATRASADO, CANCELADO };
	
	private LocalDateTime datahora;	
	private Status status;	

	public Voo(LocalDateTime datahora) {		
		this.datahora = datahora;
		this.status = Status.CONFIRMADO; // default é confirmado
	}
	
//	public Voo(Rota rota, Duration duracao) {
//		this(rota, LocalDateTime.of(2016,8,12,12,0),duracao);
//	}
	
	/*
	public Voo(Rota rota, Duration duracao) {
		this.rota = rota;
		// Cria sempre em 12/8/2016 às 12h
		this.datahora = LocalDateTime.of(2016,8,12,12,0);
		this.duracao = duracao;
		this.status = Status.CONFIRMADO; // default é confirmado
	}
	*/
	
	public abstract Rota getRota();
	public abstract Duration getDuracao();
	
	public LocalDateTime getDatahora() {
		return datahora;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status novo) {
		this.status = novo;
	}
	
	@Override
    public String toString() {
       return datahora + " " + status;
    }
}
