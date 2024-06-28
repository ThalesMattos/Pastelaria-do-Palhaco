package com.thalesmattos.pastelariadopalhaco.repository;

import com.thalesmattos.pastelariadopalhaco.models.ListaDeEspera;
import com.thalesmattos.pastelariadopalhaco.models.Requisicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaDeEsperaRepository extends JpaRepository<ListaDeEspera, Integer> {
}
