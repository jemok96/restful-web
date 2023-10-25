package com.example.restfulwebservice.di;

public class DiController {
    private final DiService service;

    public DiController(DiService service) {
        this.service = service;
    }


    //    public void setService(DiService service) {
//        Service = service;
//    }
    public void controllerService(){
        service.helloService();
    }

}
