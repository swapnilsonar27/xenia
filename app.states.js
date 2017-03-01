unidbApp
    .config([
        '$stateProvider',
        '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {


            // Use $urlRouterProvider to configure any redirects (when) and invalid urls (otherwise).
            $urlRouterProvider
                .when('/dashboard', 'login')
                .otherwise('login');

            $stateProvider
            // -- ERROR PAGES --
                .state("error", {
                    url: "/error",
                    templateUrl: 'app/views/error.html'
                })
                .state("error.404", {
                    url: "/404",
                    templateUrl: 'app/components/pages/error_404View.html'
                })
                .state("error.500", {
                    url: "/500",
                    templateUrl: 'app/components/pages/error_500View.html'
                })
            // -- LOGIN PAGE --
                .state("login", {
                    url: "/login",
                    templateUrl: 'app/core/views/loginView.html',
                    controller: 'loginCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_iCheck',
                                'app/core/controllers/loginController.js'
                            ]);
                        }]
                    }
                })
            // -- RESTRICTED --
                .state("restricted", {
                    abstract: true,
                    url: "",
                    views: {
                        'main_header': {
                            templateUrl: 'app/core/views/blocks/headerView.html',
                            controller: 'hostCtrl'
                        },
                        'main_sidebar': {
                            templateUrl: 'app/core/views/blocks/main_sidebarView.html',
                            controller: 'hostCtrl'
                        },
                        '': {
                            templateUrl: 'app/core/views/restricted.html'
                        }
                    },
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'lazy_selectizeJS',
                                'lazy_switchery',
                                'lazy_prismJS',
                                'lazy_autosize',
                                'lazy_iCheck',
                                'app/core/controllers/hostController.js'
                            ],{ serie: true });
                        }],
                        /*lang: ['userDataService',function(userDataService) {
                            return userDataService.getLang()
                        }],*/
                        nav:['userDataService',function(userDataService){
                            return userDataService.getNav()
                        }]
                    }
                })
                //dashboard
                .state("restricted.dashboard", {
                    url: "/",
                    templateUrl: 'app/reporting/views/dashboardView.html',
                    controller: 'dashboardController',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/dashboardController.js'
                            ], {serie: true} );
                        }]
                        
                    },
                    data: {
                        pageTitle: 'Dashboard'
                    }

                })
                /*Manage Themes */
                 .state("restricted.manageThemes", {
                    url: "/managethemes",
                    templateUrl: 'app/reporting/views/manageThemesView.html',
                    controller: 'MyCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/manageThemesController.js',
                                'app/reporting/services/manageThemesService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Themes'
                    }

                }).state("restricted.manageConnectionStrings", {
                    url: "/connectionstring",
                    templateUrl: 'app/reporting/views/connectionStringView.html',
                    controller: 'ConnectionStringCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/connectionStringController.js',
                                'app/reporting/services/connectionStringService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Connection Strings'
                    }

                }).state("restricted.manageDatabases", {
                    url: "/databases",
                    templateUrl: 'app/reporting/views/databasesView.html',
                    controller: 'DatabasesCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/databasesController.js',
                                'app/reporting/services/databasesService.js',
                                'app/reporting/services/olapConnectionStringsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Databases'
                    }

                }).state("restricted.manageCompanies", {
                    url: "/companies",
                    templateUrl: 'app/reporting/views/companiesView.html',
                    controller: 'CompaniesCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/companiesController.js',
                                'app/reporting/services/companiesService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Companies'
                    }

                }).state("restricted.manageScripts", {
                    url: "/scripts",
                    templateUrl: 'app/reporting/views/scriptsView.html',
                    controller: 'ScriptsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/scriptsController.js',
                                'app/reporting/services/scriptsService.js',
                                'app/reporting/services/olapScriptsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Scripts'
                    }

                }).state("restricted.manageSsis", {
                    url: "/ssis",
                    templateUrl: 'app/reporting/views/ssisView.html',
                    controller: 'SsisCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/ssisController.js',
                                'app/reporting/services/ssisService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage SSIS'
                    }

                }).state("restricted.manageModules", {
                    url: "/modules",
                    templateUrl: 'app/reporting/views/modulesView.html',
                    controller: 'ModulesCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/modulesController.js',
                                'app/reporting/services/modulesService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Modules'
                    }

                }).state("restricted.manageSubFunctions", {
                    url: "/functions",
                    templateUrl: 'app/reporting/views/functionsView.html',
                    controller: 'FunctionsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/functionsController.js',
                                'app/reporting/services/functionsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Functions'
                    }

                }).state("restricted.manageUsers", {
                    url: "/users",
                    templateUrl: 'app/reporting/views/usersView.html',
                    controller: 'UsersCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/usersController.js',
                                'app/reporting/services/usersService.js',
                                'app/reporting/services/companiesService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Users'
                    }

                }).state("restricted.manageLanguages", {
                    url: "/language",
                    templateUrl: 'app/reporting/views/languageView.html',
                    controller: 'LanguageCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/languageController.js',
                                'app/reporting/services/languageService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Languages'
                    }

                }).state("restricted.functionTranslation", {
                    url: "/functionTranslation",
                    templateUrl: 'app/reporting/views/functionTranslationView.html',
                    controller: 'functionTranslationCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/functionTranslationController.js',
                                'app/reporting/services/functionTranslationService.js',
                                 'app/reporting/services/functionsService.js',
                                  'app/reporting/services/languageService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Function Translation'
                    }

                }).state("restricted.perimeterRights", {
                    url: "/perimeterRights",
                    templateUrl: 'app/reporting/views/perimeterRightsView.html',
                    controller: 'perimeterRightsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/perimeterRightsController.js',
                                'app/reporting/services/perimeterRightsService.js',
                                'app/reporting/services/companiesService.js',
                                'app/reporting/services/usersService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Perimeter Rights'
                    }

                }).state("restricted.functionsRights", {
                    url: "/functionsRights",
                    templateUrl: 'app/reporting/views/functionsRightsView.html',
                    controller: 'functionsRightsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/functionsRightsController.js',
                                'app/reporting/services/functionsRightsService.js',
                                'app/reporting/services/companiesService.js',
                                'app/reporting/services/usersService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Functions Rights'
                    }

                }).state("restricted.subFunctionTranslation", {
                    url: "/subFunctionTranslation",
                    templateUrl: 'app/reporting/views/subFunctionTranslationView.html',
                    controller: 'subFunctionTranslationCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/subFunctionTranslationController.js',
                                'app/reporting/services/subFunctionTranslationService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Sub Function Translation'
                    }

                }).state("restricted.modulesTranslations", {
                    url: "/modulesTranslations",
                    templateUrl: 'app/reporting/views/modulesTranslationsView.html',
                    controller: 'modulesTranslationsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/modulesTranslationsController.js',
                                'app/reporting/services/modulesTranslationsService.js',
                                'app/reporting/services/modulesService.js',
                                'app/reporting/services/languageService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Translate Modules'
                    }
                }).state("restricted.categories", {
                    url: "/categories",
                    templateUrl: 'app/reporting/views/categoriesView.html',
                    controller: 'categoriesCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/categoriesController.js',
                                'app/reporting/services/categoriesService.js',
                                'app/reporting/services/languageService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Categories'
                    }
                }).state("restricted.categoriesCommunication", {
                    url: "/categoriesCommunication",
                    templateUrl: 'app/reporting/views/categoriesCommunicationView.html',
                    controller: 'categoriesCommunicationCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/categoriesCommunicationController.js',
                                'app/reporting/services/categoriesCommunicationService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Categories Communication'
                    }
                }).state("restricted.communicationType", {
                    url: "/communicationType",
                    templateUrl: 'app/reporting/views/communicationTypeView.html',
                    controller: 'communicationTypeCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/communicationTypeController.js',
                                'app/reporting/services/communicationTypeService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Communication Type'
                    }
                }).state("restricted.categoriesSubscriptions", {
                    url: "/categoriesSubscriptions",
                    templateUrl: 'app/reporting/views/categoriesSubscriptionsView.html',
                    controller: 'categoriesSubscriptionsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/categoriesSubscriptionsController.js',
                                'app/reporting/services/categoriesSubscriptionsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Categories Subscriptions'
                    }
                }).state("restricted.manageCurrencies", {
                    url: "/currency",
                    templateUrl: 'app/reporting/views/currencyView.html',
                    controller: 'CurrencyCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/currencyController.js',
                                'app/reporting/services/currencyService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage Currencies'
                    }

                }).state("restricted.mailProviders", {
                    url: "/mailProviders",
                    templateUrl: 'app/reporting/views/mailProvidersView.html',
                    controller: 'mailProvidersCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/mailProvidersController.js',
                                'app/reporting/services/mailProvidersService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Mail Providers'
                    }

                }).state("restricted.manageSmsProviders", {
                    url: "/smsProvider",
                    templateUrl: 'app/reporting/views/smsProviderView.html',
                    controller: 'smsProviderCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/smsProviderController.js',
                                'app/reporting/services/smsProviderService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Manage SMS Providers'
                    }

                }).state("restricted.categoriesOptions", {
                    url: "/categoriesOptions",
                    templateUrl: 'app/reporting/views/categoriesOptionsView.html',
                    controller: 'categoriesOptionsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/categoriesOptionsController.js',
                                'app/reporting/services/categoriesOptionsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Categories Options'
                    }
                }).state("restricted.genericTar", {
                    url: "/genericTar",
                    templateUrl: 'app/reporting/views/genericTarView.html',
                    controller: 'genericTarCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/genericTarController.js',
                                'app/reporting/services/genericTarService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Generic Tar'
                    }
                }).state("restricted.genericProducts", {
                    url: "/genericProducts",
                    templateUrl: 'app/reporting/views/genericProductsView.html',
                    controller: 'genericProductsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/genericProductsController.js',
                                'app/reporting/services/genericProductsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Generic Products'
                    }
                }).state("restricted.genericTrafficType", {
                    url: "/genericTrafficType",
                    templateUrl: 'app/reporting/views/genericTrafficTypeView.html',
                    controller: 'genericTrafficTypeCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/genericTrafficTypeController.js',
                                'app/reporting/services/genericTrafficTypeService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Generic Traffic Type'
                    }
                }).state("restricted.genericCommunications", {
                    url: "/genericCommunications",
                    templateUrl: 'app/reporting/views/genericCommunicationsView.html',
                    controller: 'genericCommunicationsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/genericCommunicationsController.js',
                                'app/reporting/services/genericCommunicationsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Generic Communications'
                    }
                }).state("restricted.genericUnits", {
                    url: "/genericUnits",
                    templateUrl: 'app/reporting/views/genericUnitsView.html',
                    controller: 'genericUnitsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/genericUnitsController.js',
                                'app/reporting/services/genericUnitsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Generic Units'
                    }
                }).state("restricted.domains", {
                    url: "/domains",
                    templateUrl: 'app/reporting/views/domainsView.html',
                    controller: 'domainsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/domainsController.js',
                                'app/reporting/services/domainsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Domains'
                    }
                }).state("restricted.countries", {
                    url: "/countries",
                    templateUrl: 'app/reporting/views/countriesView.html',
                    controller: 'countriesCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/countriesController.js',
                                'app/reporting/services/countriesService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Countries'
                    }
                }).state("restricted.cities", {
                    url: "/cities",
                    templateUrl: 'app/reporting/views/citiesView.html',
                    controller: 'citiesCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/citiesController.js',
                                'app/reporting/services/citiesService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Cities'
                    }
                }).state("restricted.olapConnectionStrings", {
                    url: "/olapConnectionStrings",
                    templateUrl: 'app/reporting/views/olapConnectionStringsView.html',
                    controller: 'olapConnectionStringsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/olapConnectionStringsController.js',
                                'app/reporting/services/olapConnectionStringsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'OLAP Connection Strings'
                    }
                }).state("restricted.olapScripts", {
                    url: "/olapScripts",
                    templateUrl: 'app/reporting/views/olapScriptsView.html',
                    controller: 'olapScriptsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/olapScriptsController.js',
                                'app/reporting/services/olapScriptsService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'OLAP Scripts'
                    }
                }).state("restricted.indicator", {
                    url: "/indicator",
                    templateUrl: 'app/reporting/views/indicatorView.html',
                    controller: 'indicatorCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/indicatorController.js',
                                'app/reporting/services/indicatorService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'Indicators'
                    }
                }).state("restricted.ssisDescription", {
                    url: "/ssisDescription",
                    templateUrl: 'app/reporting/views/ssisDescriptionView.html',
                    controller: 'ssisDescriptionCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'app/reporting/controllers/ssisDescriptionController.js',
                                'app/reporting/services/ssisDescriptionService.js'
                            ], {serie: true} );
                        }]
                    },
                    data: {
                        pageTitle: 'SSIS Description'
                    }
                })
        }
    ]);
