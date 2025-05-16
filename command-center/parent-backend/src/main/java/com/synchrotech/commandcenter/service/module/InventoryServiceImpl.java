package com.synchrotech.commandcenter.service.module;

import com.synchrotech.commandcenter.model.module.Inventory;
import com.synchrotech.commandcenter.repository.module.InventoryRepository;
import com.synchrotech.commandcenter.security.TenantContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of InventoryService.
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        inventory.setTenantId(TenantContextHolder.getTenant());
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(String id, Inventory inventory) {
        Optional<Inventory> existingOpt = inventoryRepository.findById(id);
        if (existingOpt.isEmpty()) throw new RuntimeException("Inventory not found");
        Inventory existing = existingOpt.get();
        if (!TenantContextHolder.getTenant().equals(existing.getTenantId())) {
            throw new RuntimeException("Unauthorized tenant access");
        }
        // Update fields as needed
        existing.setProductName(inventory.getProductName());
        // ... update other fields
        return inventoryRepository.save(existing);
    }

    @Override
    public Optional<Inventory> findById(String id) {
        Optional<Inventory> invOpt = inventoryRepository.findById(id);
        return invOpt.filter(i -> TenantContextHolder.getTenant().equals(i.getTenantId()));
    }

    @Override
    public List<Inventory> findByTenantId(String tenantId) {
        return inventoryRepository.findByTenantId(tenantId);
    }

    @Override
    public void deleteInventory(String id) {
        Optional<Inventory> invOpt = inventoryRepository.findById(id);
        if (invOpt.isPresent() && TenantContextHolder.getTenant().equals(invOpt.get().getTenantId())) {
            inventoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Unauthorized tenant access");
        }
    }
} 