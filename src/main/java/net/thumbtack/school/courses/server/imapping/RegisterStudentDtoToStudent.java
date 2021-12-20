package net.thumbtack.school.courses.server.imapping;

import net.thumbtack.school.courses.server.dto.request.RegisterStudentDtoRequest;
import net.thumbtack.school.courses.server.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegisterStudentDtoToStudent {

    RegisterStudentDtoToStudent MAPPER = Mappers.getMapper(RegisterStudentDtoToStudent.class);

    @Mappings({
        @Mapping(target = "idUser", ignore = true),
        @Mapping(target = "courses", ignore = true)
    })
    Student convert(RegisterStudentDtoRequest request);
}
