import { TestBed, inject } from '@angular/core/testing';

import { EbookrepositoryService } from './ebookrepository.service';

describe('EbookrepositoryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EbookrepositoryService]
    });
  });

  it('should be created', inject([EbookrepositoryService], (service: EbookrepositoryService) => {
    expect(service).toBeTruthy();
  }));
});
