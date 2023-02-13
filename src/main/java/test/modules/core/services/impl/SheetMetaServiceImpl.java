package test.modules.core.services.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import com.blinked.modules.core.exceptions.NotFoundException;
import com.blinked.modules.core.model.entities.SheetMeta;
import com.blinked.modules.core.repositories.SheetMetaRepository;
import com.blinked.modules.core.repositories.SheetRepository;
import com.blinked.modules.core.services.SheetMetaService;

import lombok.extern.slf4j.Slf4j;

/**
 * Sheet meta service implementation class.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Slf4j
@Service
public class SheetMetaServiceImpl extends BaseMetaServiceImpl<SheetMeta> implements SheetMetaService {

    private final SheetMetaRepository sheetMetaRepository;

    private final SheetRepository sheetRepository;

    public SheetMetaServiceImpl(SheetMetaRepository sheetMetaRepository,
            SheetRepository sheetRepository) {
        super(sheetMetaRepository);
        this.sheetMetaRepository = sheetMetaRepository;
        this.sheetRepository = sheetRepository;
    }

    @Override
    public void validateTarget(@NotNull Integer sheetId) {
        sheetRepository.findById(sheetId)
                .orElseThrow(() -> new NotFoundException("Can't find information on this page").setErrorData(sheetId));
    }
}
