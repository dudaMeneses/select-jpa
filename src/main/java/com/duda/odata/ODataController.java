package com.duda.odata;

import com.duda.odata.interpreter.ODataInterpreter;
import com.duda.odata.jpa.ODataJpaExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ODataController<T> {

    @Autowired
    private ODataJpaExecutor<T> executor;

    @GetMapping
    public List findAll(@RequestParam("select") List<String> selectors,
                        @RequestParam("top") Integer top,
                        @RequestParam("filter") String filter){
        return executor.findAll(ODataInterpreter.getFilter(selectors, top, filter));
    }

    @GetMapping(path = "/count")
    public Long count(@RequestParam("select") List<String> selectors,
                      @RequestParam("top") Integer top,
                      @RequestParam("filter") String filter){
        return executor.count(ODataInterpreter.getFilter(selectors, top, filter));
    }
}
