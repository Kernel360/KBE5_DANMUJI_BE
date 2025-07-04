package com.back2basics.adapter.persistence.company.adapter;

import static com.back2basics.adapter.persistence.company.QCompanyEntity.companyEntity;
import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_ALREADY_RESTORED;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.company.model.Company;
import com.back2basics.company.port.out.ReadCompanyPort;
import com.back2basics.infra.exception.company.CompanyException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadCompanyJpaAdapter implements ReadCompanyPort {

    private final JPAQueryFactory jpaQueryFactory;
    private final CompanyEntityRepository companyEntityRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Optional<Company> findById(Long id) {
        return companyEntityRepository.findByIdAndDeletedAtIsNull(id).map(companyMapper::toDomain);
    }

    @Override
    public Page<Company> findByNameContaining(Pageable pageable, String keyword) {
        return companyEntityRepository.findByNameContainingAndDeletedAtIsNull(pageable, keyword)
            .map(companyMapper::toDomain);
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        return companyEntityRepository.findByDeletedAtIsNull(pageable)
            .map(companyMapper::toDomain);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyEntityRepository.findByDeletedAtIsNull()
            .stream().map(companyMapper::toDomain).toList();
    }

    @Override
    public List<Company> getRecentCompanies() {
        return companyEntityRepository.findTop5ByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream().map(companyMapper::toDomain).toList();
    }

    @Override
    public Optional<Company> findByIdForRestore(Long companyId) {
        CompanyEntity entity = jpaQueryFactory
            .selectFrom(companyEntity)
            .where(
                companyEntity.id.eq(companyId),
                companyEntity.deletedAt.isNotNull()
            )
            .fetchOne();

        if (entity == null) {
            throw new CompanyException(COMPANY_ALREADY_RESTORED);
        }

        return Optional.of(companyMapper.toDomain(entity));
    }
}
