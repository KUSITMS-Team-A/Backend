package com.backend.domain.contract.entity;

import com.backend.common.domain.BaseEntity;
import com.backend.domain.benefit.entity.Benefit;
import com.backend.domain.contract.dto.UpdateContractRequest;
import com.backend.domain.event.entity.Event;
import com.backend.domain.store.entity.Store;
import com.backend.domain.university.entity.University;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "contract")
public class Contract extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String manager;

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "contract", cascade = CascadeType.PERSIST)
    private List<Benefit> benefits = new ArrayList<>();

    @OneToMany(mappedBy = "contract", cascade = CascadeType.PERSIST)
    private List<Event> events = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Contract(LocalDate startDate, LocalDate endDate, String manager, University university, Store store, List<Benefit> benefits) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.manager = manager;
        this.university = university;
        this.store = store;
        this.benefits = benefits;
    }

    public void expire() {
        endDate = LocalDate.now().minusDays(1L);
    }

    public void add(Event event) {
        events.add(event);
        event.add(this);
    }

    public void expire(Benefit benefit) {
        benefits.remove(benefit);
        benefit.expire();
    }

    public void expireAll() {
        for (int i = benefits.size() - 1; i >= 0 ; i--) {
            expire(benefits.get(i));
        }
    }

    public void update(UpdateContractRequest request) {
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.manager = request.getManager();
        expireAll();
        benefits.addAll(request.createBenefits(this));
    }

}
