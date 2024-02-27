import {
  CanActivateFn,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const protectedRoutes: string[] = ['/dashboard'];

  const loggedIn = authService.loggedInValue;

  console.log(loggedIn);
  console.log(protectedRoutes.includes(state.url));
  console.log(protectedRoutes.includes(state.url) && loggedIn);
  
  return protectedRoutes.includes(state.url) && !loggedIn
    ? router.navigate(['/'])
    : true;
};
