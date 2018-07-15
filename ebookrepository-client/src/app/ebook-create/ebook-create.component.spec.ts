import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EbookCreateComponent } from './ebook-create.component';

describe('EbookCreateComponent', () => {
  let component: EbookCreateComponent;
  let fixture: ComponentFixture<EbookCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EbookCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EbookCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
