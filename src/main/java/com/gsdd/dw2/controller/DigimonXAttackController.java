package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.DigimonXAttackModel;
import com.gsdd.dw2.service.DigimonXAttackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DigimonXAttack CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/")
public class DigimonXAttackController {

    private final DigimonXAttackService digimonXAttackService;

    @GetMapping("digimons/{digimonId:[0-9]+}/attacks")
    public ResponseEntity<Collection<DigimonXAttackModel>> getAllAtk(
            @PathVariable("digimonId") Long digimonId) {
        return ResponseEntity.ok(
                digimonXAttackService.getAllAtk(digimonId).stream().collect(Collectors.toList()));
    }

    @GetMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
    public ResponseEntity<DigimonXAttackModel> getById(
            @PathVariable("digimonId") Long digimonId, @PathVariable("attackId") Long attackId) {
        DigimonXAttackModel dxa = digimonXAttackService.getById(digimonId, attackId);
        return Optional.ofNullable(dxa)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
    public ResponseEntity<DigimonXAttackModel> associate(
            @PathVariable("digimonId") Long digimonId, @PathVariable("attackId") Long attackId) {
        return Optional.ofNullable(digimonXAttackService.associate(digimonId, attackId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
    public ResponseEntity<Object> deassociate(
            @PathVariable("digimonId") Long digimonId, @PathVariable("attackId") Long attackId) {
        return Optional.ofNullable(digimonXAttackService.deassociate(digimonId, attackId))
                .map(result -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
