package com.thalesmattos.pastelariadopalhaco.repository;

import com.thalesmattos.pastelariadopalhaco.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
