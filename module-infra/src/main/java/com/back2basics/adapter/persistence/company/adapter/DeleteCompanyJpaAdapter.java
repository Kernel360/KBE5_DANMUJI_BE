<<<<<<<< HEAD:module-infra/src/main/java/com/back2basics/adapter/persistence/company/adapter/CompanyJpaAdapter.java
<<<<<<<< HEAD:module-infra/src/main/java/com/back2basics/adapter/persistence/company/adapter/CompanyJpaAdapter.java
package com.back2basics.adapter.persistence.company.adapter;
========
package com.back2basics.adapter.persistence.company;
>>>>>>>> 5d1ab33d6cebe93e15893a9398c646a31e935e34:module-infra/src/main/java/com/back2basics/adapter/persistence/company/CompanyJpaAdapter.java
========
package com.back2basics.adapter.persistence.company.adapter;
>>>>>>>> 97667d879d3c0cfcd7a302eeb02c437a8104ef0f:module-infra/src/main/java/com/back2basics/adapter/persistence/company/adapter/DeleteCompanyJpaAdapter.java

import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.company.port.out.DeleteCompanyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCompanyJpaAdapter implements DeleteCompanyPort {

    private final CompanyEntityRepository companyEntityRepository;

    @Override
    public void deleteById(Long id) {
        companyEntityRepository.deleteById(id);
    }

}
