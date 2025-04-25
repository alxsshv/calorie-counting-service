package com.alxsshv.service.implementation;

import com.alxsshv.dto.DishDto;
import com.alxsshv.dto.mappers.DishMapper;
import com.alxsshv.exception.DataProcessingException;
import com.alxsshv.model.Dish;
import com.alxsshv.repository.DishRepository;
import com.alxsshv.service.DishService;
import com.alxsshv.service.validation.DishNotExist;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**Класс, реализующий логику работы
 * с сущностью {@link com.alxsshv.model.Dish}.
 * @author Шварёв Алексей
 * @version 1.0*/
@Service
@Validated
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class DishServiceImpl implements DishService {
    /**Репозиторий для получения сведений
     * о блюдах из базы данных.*/
    @Autowired
    private DishRepository dishRepository;
    /**Интерфеейс для двухстороннего преобразования
     * сущности {@link Dish} в DTO {@link DishDto}.
     */
    @Autowired
    private DishMapper dishMapper;

    /**Метод создания записи о блюде.
     * @param dishDto - объект передачи данных
     * для {@link com.alxsshv.model.Dish}.
     */
    @Override
    @Transactional
    public void createDish(@Valid @DishNotExist final DishDto dishDto) {
      Dish dish = dishMapper.toEntity(dishDto);
      dishRepository.save(dish);
    }

    /**Метод предназначен для получения всех записей о блюдах.
     * @return возвращает список объектов передачи
     * данных для класса {@link Dish}*/
    @Override
    public List<DishDto> findAll() {
        List<Dish> dishes = dishRepository.findAll();
        return dishMapper.toDishDtoList(dishes);
    }

    /**Метод поиска записи о блюде по id.
     * @param id - числовой идентификатор блюда в формате long.
     * @return возвращает объект класса {@link Dish}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным
     * идентификатором (id) не найдена*/
    @Override
    public Dish getById(@Min(value = 1,
            message = "Неверный формат id") final long id) {
        Optional<Dish> dishOpt = dishRepository.findById(id);
        if (dishOpt.isEmpty()) {
            throw new EntityNotFoundException("Блюдо не найдено");
        }
        return dishOpt.get();
    }

    /**Метод поиска записи о блюде по id.
     * @param id - числовой идентификатор блюда в формате long.
     * @return возвращает объект класса {@link DishDto}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным
     * идентификатором (id) не найдена*/
    @Override
    public DishDto findById(@Min(value = 1,
            message = "Неверный формат id") final long id) {
        Dish dish = getById(id);
        return dishMapper.toDishDto(dish);
    }

    /**Метод изменения данных в записи о блюде.
     * @param dishDto - объект передачи данных
     * для {@link com.alxsshv.model.Dish}.
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным в dishDto
     * идентификатором (id) не найдена*/
    @Override
    @Transactional
    public void updateDish(@Valid final DishDto dishDto) {
        Dish dishFromDb = getById(dishDto.getId());
        if (!dishFromDb.getTitle().equals(dishDto.getTitle())) {
            Optional<Dish> dishOpt = dishRepository.findByTitle(dishDto.getTitle());
            if (dishOpt.isPresent()) {
                String errorMessage = "Блюдо с таким названием уже существует."
                        + " У двух блюд не может быть одинаковых названий";
                log.error(errorMessage);
                throw new DataProcessingException(errorMessage);
            }
        }
        dishMapper.updateDishFromDishDto(dishFromDb, dishDto);
        dishRepository.save(dishFromDb);
    }

    /**Метод удаления записи о блюде по id.
     * @param id - числовой идентификатор пользователя в формате long..
     * @exception jakarta.persistence.EntityNotFoundException
     * должно выбрасываться если запись о блюде с указанным
     * идентификатором (id) не найдена*/
    @Override
    public void deleteById(@Min(value = 1,
            message = "Неверный формат id") final long id) {
        Dish dish = getById(id);
        dishRepository.delete(dish);
    }
}
