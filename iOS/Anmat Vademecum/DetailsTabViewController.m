//
//  DetailsTabViewController.m
//  Anmat Vademecum
//
//  Created by mag on 2/20/15.
//  Copyright (c) 2015 Think Up Studios. All rights reserved.
//

#import "DetailsTabViewController.h"
#import "SearchResultsViewController.h"
#import "MainDetailsViewController.h"
#import "MedicineService.h"
#import "Medicine.h"
#import "ContactAlertController.h"
#import "MedicinesFilter.h"
#import "MenuViewController.h"

@interface DetailsTabViewController ()

@end

@implementation DetailsTabViewController {
    MedicineService *medicineService;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.navigationItem.title = @"Detalle";
    
    UIBarButtonItem *btnContact = [[UIBarButtonItem alloc] init];
    UIImage *imgContact = [UIImage imageNamed:@"Contact"];
    
    [btnContact setImage:imgContact];
    [btnContact setTarget:self];
    [btnContact setAction:@selector(showContactInfo:)];
    
    UIBarButtonItem *btnRelated = [[UIBarButtonItem alloc] init];
    UIImage *imgRelated = [UIImage imageNamed:@"Recommended"];
    
    [btnRelated setImage:imgRelated];
    [btnRelated setTarget:self];
    [btnRelated setAction:@selector(showRelated:)];
    
    UIBarButtonItem *btnHome = [[UIBarButtonItem alloc] init];
    UIImage *imgHome = [UIImage imageNamed:@"Home"];
    
    [btnHome setImage:imgHome];
    [btnHome setTarget:self];
    [btnHome setAction:@selector(showHome:)];
    
    self.navigationItem.rightBarButtonItems = [NSArray arrayWithObjects:btnContact, btnRelated, btnHome, nil];
    
    if ([self respondsToSelector:@selector(edgesForExtendedLayout)]) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        self.extendedLayoutIncludesOpaqueBars = NO;
        self.automaticallyAdjustsScrollViewInsets = NO;
    }
    
    medicineService = [[MedicineService alloc] init];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

-(void) showContactInfo:(id) sender {
    ContactAlertController *contactSheet = [ContactAlertController alertControllerWithTitle:@"ANMAT Responde" message:@"Datos de contacto" preferredStyle:UIAlertControllerStyleActionSheet];
    
    contactSheet.popoverPresentationController.barButtonItem = self.navigationItem.rightBarButtonItems[1];
    contactSheet.popoverPresentationController.sourceView = self.view;
    
    [self
     presentViewController:contactSheet animated:YES completion:nil];
}

-(void) showRelated:(id) sender {
    SearchResultsViewController *searchResults = (SearchResultsViewController *) [self.storyboard instantiateViewControllerWithIdentifier:@"SearchResultsViewController"];
    MainDetailsViewController *mainDetails = (MainDetailsViewController *)[self.viewControllers firstObject];
    MedicinesFilter *filter = [[MedicinesFilter alloc] init];
    
    filter.genericName = mainDetails.medicine.genericName;
    searchResults.searchFilter = filter;
    searchResults.title = @"Mismo Nombre Genérico";
    
    [self.navigationController pushViewController:searchResults animated:YES];
}

-(void) showHome:(id) sender {
    MenuViewController *about = (MenuViewController *) [self.storyboard instantiateViewControllerWithIdentifier:@"MenuViewController"];
    
    [self.navigationController pushViewController:about animated:YES];
}

@end
