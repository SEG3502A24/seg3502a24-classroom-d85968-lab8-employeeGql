package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeeRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

@Controller
class EmployeesResolver (
    private val mongoOperations: MongoOperations,
    private val employeeRepository: EmployeeRepository)
{
@QueryMapping
    fun employees(@Argument email: String): List<Employee> {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))
        return mongoOperations.find(query, Employee::class.java)
    }

    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = input.name,
            dateOfBirth = input.dateOfBirth,
            city = input.city,
            salary = input.salary,
            gender = input.gender,
            email = input.email
        )
        employeeRepository.save(employee)
        return employee
    }
}
