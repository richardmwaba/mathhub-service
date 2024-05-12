package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssetType;
import com.hubformath.mathhubservice.repository.systemconfig.AssetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetTypeServiceTest {

    @Mock
    private AssetTypeRepository assetTypeRepository;

    private AssetTypeService assetTypeService;

    @BeforeEach
    void setup() {
        assetTypeService = new AssetTypeService(assetTypeRepository);
    }

    @Test
    void shouldReturnAllAssetTypes() {
        when(assetTypeRepository.findAll()).thenReturn(getAssetTypes());

        assertThat(assetTypeService.getAllAssetTypes()).isEqualTo(getAssetTypes());
    }

    @Test
    void shouldReturnAssetTypeById() {
        AssetType assetType = getAssetTypes().get(0);
        when(assetTypeRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(assetType));

        assertThat(assetTypeService.getAssetTypeById("1")).isEqualTo(assetType);
    }

    @Test
    void shouldCreateAssetType() {
        AssetType expectedAssetType = new AssetType();
        expectedAssetType.setAssetTypeId(UUID.randomUUID().toString());
        expectedAssetType.setTypeName("Cash flow");
        expectedAssetType.setTypeDescription("Cash flow");
        when(assetTypeRepository.save(expectedAssetType)).thenReturn(expectedAssetType);

        AssetType actualAssetType = assetTypeService.createAssetType(expectedAssetType);

        verify(assetTypeRepository).save(expectedAssetType);
        assertThat(actualAssetType).isEqualTo(expectedAssetType);
    }

    @Test
    void shouldUpdateAssetType() {
        AssetType assetType = getAssetTypes().get(0);
        AssetType updatedAssetTypeRequest = new AssetType("1",
                                                          "Fixed assets",
                                                          "Fixed assets like real estate, machinery, etc.");
        when(assetTypeRepository.findById("1")).thenReturn(Optional.of(assetType));
        when(assetTypeRepository.save(updatedAssetTypeRequest)).thenReturn(updatedAssetTypeRequest);

        AssetType updatedAssetType = assetTypeService.updateAssetType("1", updatedAssetTypeRequest);

        assertThat(updatedAssetType.getTypeName()).isEqualTo("Fixed assets");
        assertThat(updatedAssetType.getTypeDescription()).isEqualTo("Fixed assets like real estate, machinery, etc.");
    }

    @Test
    void shouldDeleteAssetType() {
        AssetType assetType = getAssetTypes().get(0);
        when(assetTypeRepository.findById("1")).thenReturn(Optional.of(assetType));

        assetTypeService.deleteAssetType("1");

        verify(assetTypeRepository).delete(assetType);
    }

    private List<AssetType> getAssetTypes() {
        return List.of(
                new AssetType("1", "Fixed assets", "Fixed assets like land, building, etc."),
                new AssetType("2", "Current assets", "Current assets like cash, inventory, etc.")
                      );
    }
}