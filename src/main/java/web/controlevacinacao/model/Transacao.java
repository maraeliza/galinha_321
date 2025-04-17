package web.controlevacinacao.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
public class Transacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "data_transacao", nullable = false)
    private LocalDateTime dataTransacao; 

    @Column(name = "moeda_id", nullable = false)
    private Integer moedaId;
    
    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor; 

    @Column(name = "tipo", nullable = false)
    private Boolean tipo;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId; 

    @Column(name = "bot_id", nullable = false)
    private Long botId;

    public Transacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public Integer getMoedaId() {
        return moedaId;
    }

    public void setMoedaId(Integer moedaId) {
        this.moedaId = moedaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getBotId() {
        return botId;
    }

    public void setBotId(Long botId) {
        this.botId = botId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transacao other = (Transacao) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transacao [id=" + id + ", dataTransacao=" + dataTransacao +
                ", moedaId=" + moedaId + ", valor=" + valor + ", tipo=" + tipo +
                ", usuarioId=" + usuarioId + ", botId=" + botId + "]";
    }
}
