package com.dbulgakov.other.di;

import com.dbulgakov.task2.other.di.AppComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModelTestModule.class, PresenterTestModule.class, ViewTestModule.class, DataTestModule.class})
public interface TestComponent extends AppComponent {


    void inject(ModelImplTest dataRepositoryImplTest);

    void inject(RepoInfoPresenterTest repoInfoPresenterTest);

    void inject(RepoListPresenterTest repoListPresenterTest);

    void inject(RepoBranchesMapperTest repoBranchesMapperTest);

    void inject(RepoContributorsMapperTest repoContributorsMapperTest);

    void inject(RepoListMapperTest userReposMapperTest);

    void inject(RepoInfoFragmentTest repoInfoFragmentTest);

    void inject(RepoListFragmentTest repoListFragmentTest);
}
