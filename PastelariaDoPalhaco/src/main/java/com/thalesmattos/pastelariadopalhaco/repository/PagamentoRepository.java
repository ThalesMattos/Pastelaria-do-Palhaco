package com.thalesmattos.pastelariadopalhaco.repository;

import com.thalesmattos.pastelariadopalhaco.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
