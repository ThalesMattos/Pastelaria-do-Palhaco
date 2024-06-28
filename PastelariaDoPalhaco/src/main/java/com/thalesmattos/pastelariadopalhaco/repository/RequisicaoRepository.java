package com.thalesmattos.pastelariadopalhaco.repository;


import com.thalesmattos.pastelariadopalhaco.models.Requisicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisicaoRepository extends JpaRepository<Requisicao, Integer> {
}
