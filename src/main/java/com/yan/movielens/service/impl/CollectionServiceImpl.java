package com.yan.movielens.service.impl;


import com.yan.movielens.entity.Collection;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.PageEntity;
import com.yan.movielens.repository.CollectionRepository;
import com.yan.movielens.service.CollectionService;
import com.yan.movielens.service.MovieService;
import com.yan.movielens.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private MovieService movieService;

    @Override
    public Collection saveCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    public void deleteCollection(Integer userId, Integer movieId) {
        Collection collection=new Collection();
        collection.getKey().setUserId(userId);
        collection.getKey().setMovieId(movieId);

        collectionRepository.delete(collection);
    }

    public List<Integer> getCollectionById(Integer userId) {
        return collectionRepository.getCollectionListById(userId);
    }

    @Override
    public PageEntity getColListById(Integer userId, Integer pageIndex, Integer pageSize){
        List<Integer> movieIds=getCollectionById(userId);
        List<MovieDetails> movieDetailsList=new ArrayList<>();
        for (Integer movieId:movieIds){
            Optional<MovieDetails> movieDetails=movieService.getDetailsById(movieId);
            if(movieDetails.isPresent()){
                movieDetailsList.add(movieDetails.get());
            }
        }

        //进行分页
        PageHelper pageHelper=new PageHelper(movieDetailsList,pageSize);
        PageEntity pageEntity=new PageEntity();
        pageEntity.setList(pageHelper.getPage(pageIndex));
        pageEntity.setPageNum(pageHelper.getPageNum());
        pageEntity.setTotal(pageHelper.getDataSize());
        return pageEntity;

    }
}
