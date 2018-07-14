import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EbookListSearchComponent } from './ebook-list-search.component';

describe('EbookListSearchComponent', () => {
  let component: EbookListSearchComponent;
  let fixture: ComponentFixture<EbookListSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EbookListSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EbookListSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
