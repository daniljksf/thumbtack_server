package net.thumbtack.school.courses.server.imapping;

import net.thumbtack.school.courses.server.dto.request.RegisterCourseDtoRequest;
import net.thumbtack.school.courses.server.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegisterCourseDtoToCourse {

    RegisterCourseDtoToCourse MAPPER = Mappers.getMapper(RegisterCourseDtoToCourse.class);

    @Mappings({
        @Mapping(target = "idCourse", ignore = true),
        @Mapping(target = "students", ignore = true)
    })
    Course convert(RegisterCourseDtoRequest request);

}
