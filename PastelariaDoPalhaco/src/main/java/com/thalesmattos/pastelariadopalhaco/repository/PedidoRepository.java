package com.thalesmattos.pastelariadopalhaco.repository;

import com.thalesmattos.pastelariadopalhaco.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
