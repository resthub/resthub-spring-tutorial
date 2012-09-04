package org.resthub.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.resthub.training.model.Sample;

public interface SampleRepository extends JpaRepository<Sample, Long> {

}
