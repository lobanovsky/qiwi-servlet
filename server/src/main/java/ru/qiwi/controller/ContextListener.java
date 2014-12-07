package ru.qiwi.controller;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener extends ContextLoaderListener {

}
