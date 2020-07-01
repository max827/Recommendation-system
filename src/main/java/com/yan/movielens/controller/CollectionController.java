package com.yan.movielens.controller;


import com.yan.movielens.entity.Collection;
import com.yan.movielens.entity.model.MovieDetails;
import com.yan.movielens.entity.model.PageEntity;
import com.yan.movielens.service.CollectionService;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping(value = "/collection")
public class CollectionController {

    private CollectionService collectionService;

    public CollectionController(CollectionService collectionService){
        this.collectionService=collectionService;
    }

    @GetMapping(value = "/list")
    public PageEntity getColListById(@RequestParam(value = "userId") Integer userId,
                                     @RequestParam(value = "pageIndex") Integer pageIndex,
                                     @RequestParam(value = "pageSize") Integer pageSize){
        return collectionService.getColListById(userId,pageIndex,pageSize);
    }

    @PostMapping(value = "/delete")
    public void deleteCollection(@RequestParam(value = "userId") Integer userId,
                                 @RequestParam(value = "movieId") Integer movidId){
        collectionService.deleteCollection(userId,movidId);
    }

    @PostMapping(value = "/save")
    public void saveCollection(@RequestParam(value = "userId") Integer userId,
                               @RequestParam(value = "movieId") Integer movidId,
                               @RequestParam(value = "timeStamp") long timeStamp){
        Collection collection=new Collection();
        collection.getKey().setMovieId(movidId);
        collection.getKey().setUserId(userId);
        collection.setTimeStamp(timeStamp);

        collectionService.saveCollection(collection);
    }
}
