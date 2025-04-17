package web.controlevacinacao.filter;

import java.time.LocalDateTime;

public class TransacaoFilter {

    private Long usuarioId; 
    private Integer moedaId; 
    private Boolean tipo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim; 

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getMoedaId() {
        return moedaId;
    }

    public void setMoedaId(Integer moedaId) {
        this.moedaId = moedaId;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "TransacaoFilter [usuarioId=" + usuarioId +
                ", moedaId=" + moedaId +
                ", tipo=" + tipo +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim + "]";
    }
}
