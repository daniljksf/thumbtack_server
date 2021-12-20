package net.thumbtack.school.courses.server.imapping;

import net.thumbtack.school.courses.server.dto.request.RegisterCourseDtoRequest;
import net.thumbtack.school.courses.server.dto.request.RegisterTeacherDtoRequest;
import net.thumbtack.school.courses.server.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegisterTeacherDtoToTeacher {

    RegisterTeacherDtoToTeacher MAPPER = Mappers.getMapper(RegisterTeacherDtoToTeacher.class);

    @Mappings({
        @Mapping(target = "idUser", ignore = true),
        @Mapping(target = "courses", ignore = true)
    })
    Teacher convert(RegisterTeacherDtoRequest request);

}
