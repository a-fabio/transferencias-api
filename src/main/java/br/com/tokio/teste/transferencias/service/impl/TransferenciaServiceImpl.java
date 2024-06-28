package br.com.tokio.teste.transferencias.service.impl;

import br.com.tokio.teste.transferencias.dto.PaginacaoDTO;
import br.com.tokio.teste.transferencias.dto.TransferenciaDTO;
import br.com.tokio.teste.transferencias.model.Transferencia;
import br.com.tokio.teste.transferencias.paged.Pagina;
import br.com.tokio.teste.transferencias.repository.TransferenciaRepository;
import br.com.tokio.teste.transferencias.service.TaxaService;
import br.com.tokio.teste.transferencias.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;
    @Autowired
    TaxaService taxaService;

    @Override
    public Pagina<Transferencia> listar(PaginacaoDTO paginacaoDTO) {
        return Pagina.fromPage(transferenciaRepository.findAll(paginacaoDTO.toPageable()));
    }

    @Override
    public Transferencia cadastrar(TransferenciaDTO transferencia) {
        Transferencia transferenciaPosCalculos = Transferencia.builder()
                .numeroContaOrigem(transferencia.getNumeroContaOrigem())
                .numeroContaDestino(transferencia.getNumeroContaDestino())
                .valor(transferencia.getValor())
                .dataRealizacao(transferencia.getDataRealizacaoTransferencia())
                .valorComTaxas(taxaService.calcularValorTotalTransferencia(transferencia.getDataRealizacaoTransferencia(), transferencia.getValor()))
                .taxaAplicada(taxaService.encontrarTaxaParaDataTransferencia(transferencia.getDataRealizacaoTransferencia()))
                .build();

        return transferenciaRepository.save(transferenciaPosCalculos);
    }
}