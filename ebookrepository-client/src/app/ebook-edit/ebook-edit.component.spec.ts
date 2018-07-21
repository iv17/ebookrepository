import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EbookEditComponent } from './ebook-edit.component';

describe('EbookEditComponent', () => {
  let component: EbookEditComponent;
  let fixture: ComponentFixture<EbookEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EbookEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EbookEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
